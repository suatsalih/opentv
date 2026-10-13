/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.premium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.opentv.R
import app.opentv.core.findActivity
import app.opentv.ui.theme.Button
import app.opentv.ui.theme.TextButton

/**
 * The OpenTV Premium dialog. Mounted once over the whole app (like the update prompt); any gated
 * feature raises it through [Premium.require], and Settings → Premium opens it directly.
 */
@Composable
fun PremiumGate() {
    val showing by Premium.dialog.collectAsState()
    if (!showing) return
    val owned by Premium.owned.collectAsState()
    val price by Premium.price.collectAsState()
    val message by Premium.message.collectAsState()
    val context = LocalContext.current

    if (owned) {
        AlertDialog(
            onDismissRequest = Premium::dismissDialog,
            confirmButton = { TextButton(onClick = Premium::dismissDialog) { Text(stringResource(R.string.common_done)) } },
            title = { Text(stringResource(R.string.premium_owned_title)) },
            text = { Text(stringResource(R.string.premium_owned_body)) },
        )
        return
    }

    AlertDialog(
        onDismissRequest = Premium::dismissDialog,
        confirmButton = {
            if (Premium.purchasable) {
                Button(onClick = { context.findActivity()?.let(Premium::buy) }) {
                    Text(price?.let { stringResource(R.string.premium_buy_price, it) } ?: stringResource(R.string.premium_buy))
                }
            }
        },
        dismissButton = {
            if (Premium.purchasable) {
                TextButton(onClick = Premium::restore) { Text(stringResource(R.string.premium_restore)) }
            }
            TextButton(onClick = Premium::dismissDialog) { Text(stringResource(R.string.premium_not_now)) }
        },
        title = { Text(stringResource(R.string.premium_title)) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(stringResource(R.string.premium_body))
                message?.let {
                    Text(
                        stringResource(
                            when (it) {
                                PremiumMessage.PENDING -> R.string.premium_msg_pending
                                PremiumMessage.UNAVAILABLE -> R.string.premium_msg_unavailable
                                PremiumMessage.NOTHING_TO_RESTORE -> R.string.premium_msg_nothing_to_restore
                                PremiumMessage.ERROR -> R.string.premium_msg_error
                            },
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
    )
}

/**
 * Shown in place of a whole Premium section (Movies, Series, Recordings) when it is locked: says
 * what it is and offers the upgrade, rather than an empty tab.
 */
@Composable
fun PremiumLockedPanel(title: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.widthIn(max = 560.dp).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(title, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
            Text(
                stringResource(R.string.premium_locked_body),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Button(onClick = Premium::showDialog) { Text(stringResource(R.string.premium_see)) }
        }
    }
}
