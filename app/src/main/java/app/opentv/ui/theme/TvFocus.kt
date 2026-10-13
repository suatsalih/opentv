/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.ui.theme

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material3.ripple
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * The app-wide indication for anything clickable: the usual touch ripple, plus — when a remote or
 * keyboard moves focus onto it — a tinted fill and a solid outline. Material's own focus effect is
 * a faint tint that can't be seen on a TV across the room, so lists built from plain clickable
 * rows looked as if the cursor wasn't moving. Touch screens never focus these, so phones look
 * unchanged.
 */
internal class TvFocusIndication(private val color: Color) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode =
        FocusNode(interactionSource, color)

    override fun equals(other: Any?): Boolean = other is TvFocusIndication && other.color == color

    override fun hashCode(): Int = color.hashCode()

    private class FocusNode(
        private val interactionSource: InteractionSource,
        private val color: Color,
    ) : DelegatingNode(), DrawModifierNode {

        private var focused = false

        init {
            delegate(ripple().create(interactionSource))
        }

        override fun onAttach() {
            coroutineScope.launch {
                var count = 0
                interactionSource.interactions.collect { interaction ->
                    when (interaction) {
                        is FocusInteraction.Focus -> count++
                        is FocusInteraction.Unfocus -> count--
                        else -> return@collect
                    }
                    val now = count > 0
                    if (now != focused) {
                        focused = now
                        invalidateDraw()
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            drawContent()
            if (!focused) return
            val radius = CornerRadius(10.dp.toPx())
            val stroke = 3.dp.toPx()
            drawRoundRect(color.copy(alpha = 0.22f), cornerRadius = radius)
            drawRoundRect(color, cornerRadius = radius, style = Stroke(stroke))
        }
    }
}
