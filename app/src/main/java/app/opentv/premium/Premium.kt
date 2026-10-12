/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.premium

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * OpenTV Premium: a one-off purchase that unlocks everything beyond Live TV and the guide for one
 * provider — several providers, catch-up, recording, movies & series, and extra profiles.
 *
 * The free edition is meant to be genuinely useful on its own. Every gated feature asks
 * [require] at the moment the user reaches for it; when Premium is not owned that raises the
 * Premium dialog ([PremiumGate]) instead of a dead end.
 *
 * How ownership is established is edition-specific ([PremiumBackend]): the Google Play edition
 * uses Play Billing; the sideloaded edition currently has everything unlocked.
 */
object Premium {

    /** The features Premium unlocks. Passed to [require] so each call site says what it gates. */
    enum class Feature { PROVIDERS, CATCHUP, RECORDING, MOVIES_SERIES, PROFILES }

    private val _owned = MutableStateFlow(false)
    /** True once Premium is owned (or the edition has no paywall). */
    val owned: StateFlow<Boolean> = _owned.asStateFlow()

    private val _price = MutableStateFlow<String?>(null)
    /** The store's localised price ("£9.99"), once known. Null until then, or where there is no store. */
    val price: StateFlow<String?> = _price.asStateFlow()

    private val _dialog = MutableStateFlow(false)
    /** Whether the Premium dialog is showing. */
    val dialog: StateFlow<Boolean> = _dialog.asStateFlow()

    private val _message = MutableStateFlow<PremiumMessage?>(null)
    /** A one-line status for the dialog: purchase pending, store unavailable, nothing to restore… */
    val message: StateFlow<PremiumMessage?> = _message.asStateFlow()

    fun start(context: Context) = PremiumBackend.start(context.applicationContext)

    /**
     * True when the feature may be used. Otherwise shows the Premium dialog and returns false, so a
     * call site reads naturally: `if (Premium.require(Feature.RECORDING)) record()`.
     */
    fun require(@Suppress("UNUSED_PARAMETER") feature: Feature): Boolean {
        if (_owned.value) return true
        showDialog()
        return false
    }

    fun showDialog() {
        _message.value = null
        _dialog.value = true
    }

    fun dismissDialog() {
        _dialog.value = false
    }

    fun buy(activity: Activity) = PremiumBackend.buy(activity)

    fun restore() = PremiumBackend.restore()

    /** True when this edition sells Premium at all (the dialog hides Buy/Restore otherwise). */
    val purchasable: Boolean get() = PremiumBackend.PURCHASABLE

    // ---- Called by the edition's backend ---------------------------------------------------------

    internal fun setOwned(value: Boolean) {
        _owned.value = value
        if (value) _message.value = null
    }

    internal fun setPrice(value: String?) {
        _price.value = value
    }

    internal fun setMessage(value: PremiumMessage?) {
        _message.value = value
    }
}

/** Status lines the dialog can show. Kept as an enum so the text is translated in one place. */
enum class PremiumMessage { PENDING, UNAVAILABLE, NOTHING_TO_RESTORE, ERROR }
