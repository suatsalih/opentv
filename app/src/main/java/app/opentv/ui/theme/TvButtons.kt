/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/*
 * The app's buttons: Material's, plus an unmistakable remote-control cursor. Material marks a
 * focused button with a faint tint of its own colour — invisible on a filled button, and too subtle
 * on the rest from across the room — so while a button has focus it grows slightly and gets a
 * solid outline ring. Same names and parameters as Material's, so screens import these instead.
 * Touch screens never focus buttons, so phones look unchanged.
 */

/** A solid ring and a slight lift around whatever has the remote's focus. */
fun Modifier.tvFocusRing(shape: Shape): Modifier = composed {
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.06f else 1f, label = "focusScale")
    val ring = MaterialTheme.colorScheme.onSurface
    this
        .onFocusChanged { focused = it.hasFocus }
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .then(if (focused) Modifier.border(3.dp, ring, shape) else Modifier)
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = androidx.compose.material3.Button(
    onClick = onClick,
    modifier = modifier.tvFocusRing(ButtonDefaults.shape),
    enabled = enabled,
    content = content,
)

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = androidx.compose.material3.OutlinedButton(
    onClick = onClick,
    modifier = modifier.tvFocusRing(ButtonDefaults.outlinedShape),
    enabled = enabled,
    content = content,
)

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) = androidx.compose.material3.TextButton(
    onClick = onClick,
    modifier = modifier.tvFocusRing(ButtonDefaults.textShape),
    enabled = enabled,
    content = content,
)

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) = androidx.compose.material3.IconButton(
    onClick = onClick,
    modifier = modifier.tvFocusRing(CircleShape),
    enabled = enabled,
    content = content,
)

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = androidx.compose.material3.FilterChip(
    selected = selected,
    onClick = onClick,
    label = label,
    modifier = modifier.tvFocusRing(FilterChipDefaults.shape),
    enabled = enabled,
)
