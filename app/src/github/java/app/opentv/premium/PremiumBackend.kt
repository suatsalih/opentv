/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.premium

import android.app.Activity
import android.content.Context

/**
 * Sideloaded (GitHub / Downloader) edition: no store to sell through, so everything is unlocked.
 * Supporters can donate through the PayPal link on the About screen instead.
 */
internal object PremiumBackend {
    const val PURCHASABLE = false

    fun start(@Suppress("UNUSED_PARAMETER") context: Context) {
        Premium.setOwned(true)
    }

    fun buy(@Suppress("UNUSED_PARAMETER") activity: Activity) = Unit

    fun restore() = Unit
}
