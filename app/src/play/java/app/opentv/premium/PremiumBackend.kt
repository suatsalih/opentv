/*
 * This file is part of OpenTV.
 * Copyright (C) 2026 The OpenTV Contributors
 * Licensed under the GNU General Public License v3.0 or later.
 */
package app.opentv.premium

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams

/**
 * Google Play edition: Premium is a one-time, non-consumable in-app product bought through Play
 * Billing. Play is the source of truth — purchases are re-read from Play on every start, so a
 * refund revokes Premium and a new device restores it. The last answer is cached so the app opens
 * already unlocked even before Play has replied (or with no connection at all).
 */
internal object PremiumBackend : PurchasesUpdatedListener {
    const val PURCHASABLE = true

    /** The in-app product's ID in Play Console (Monetize → Products → In-app products). */
    const val PRODUCT_ID = "premium_unlock"

    private const val PREFS = "premium"
    private const val KEY_OWNED = "owned"

    private lateinit var prefs: SharedPreferences
    private var client: BillingClient? = null
    private var details: ProductDetails? = null

    fun start(context: Context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        Premium.setOwned(prefs.getBoolean(KEY_OWNED, false))
        val billing = BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .enableAutoServiceReconnection()
            .build()
        client = billing
        connect(billing)
    }

    fun buy(activity: Activity) {
        val billing = client
        val product = details
        if (billing == null || !billing.isReady || product == null) {
            Premium.setMessage(PremiumMessage.UNAVAILABLE)
            billing?.let(::connect)
            return
        }
        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(product).build()),
            )
            .build()
        val result = billing.launchBillingFlow(activity, params)
        if (result.responseCode != BillingClient.BillingResponseCode.OK) {
            Premium.setMessage(PremiumMessage.ERROR)
        }
    }

    fun restore() {
        val billing = client
        if (billing == null || !billing.isReady) {
            Premium.setMessage(PremiumMessage.UNAVAILABLE)
            billing?.let(::connect)
            return
        }
        queryPurchases(userAsked = true)
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        when (result.responseCode) {
            BillingClient.BillingResponseCode.OK -> handle(purchases.orEmpty(), userAsked = false)
            BillingClient.BillingResponseCode.USER_CANCELED -> Unit
            // Bought earlier (another device, or a reinstall): read it back from Play.
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> queryPurchases(userAsked = true)
            else -> Premium.setMessage(PremiumMessage.ERROR)
        }
    }

    private fun connect(billing: BillingClient) {
        billing.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(result: BillingResult) {
                if (result.responseCode != BillingClient.BillingResponseCode.OK) return
                queryProduct()
                queryPurchases(userAsked = false)
            }

            // Reconnection is automatic (enableAutoServiceReconnection); nothing to do here.
            override fun onBillingServiceDisconnected() = Unit
        })
    }

    private fun queryProduct() {
        val billing = client ?: return
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(PRODUCT_ID)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build(),
                ),
            )
            .build()
        billing.queryProductDetailsAsync(params) { result, productDetails ->
            if (result.responseCode != BillingClient.BillingResponseCode.OK) return@queryProductDetailsAsync
            details = productDetails.productDetailsList.firstOrNull()
            Premium.setPrice(details?.oneTimePurchaseOfferDetails?.formattedPrice)
        }
    }

    private fun queryPurchases(userAsked: Boolean) {
        val billing = client ?: return
        val params = QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
        billing.queryPurchasesAsync(params) { result, purchases ->
            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                handle(purchases, userAsked)
            } else if (userAsked) {
                Premium.setMessage(PremiumMessage.UNAVAILABLE)
            }
        }
    }

    /**
     * Applies Play's list of purchases. A purchase must be acknowledged within three days or Play
     * refunds it automatically; a pending one (cash payment, family approval) unlocks nothing yet.
     */
    private fun handle(purchases: List<Purchase>, userAsked: Boolean) {
        val ours = purchases.filter { PRODUCT_ID in it.products }
        val bought = ours.filter { it.purchaseState == Purchase.PurchaseState.PURCHASED }
        val pending = ours.any { it.purchaseState == Purchase.PurchaseState.PENDING }

        for (purchase in bought) {
            if (!purchase.isAcknowledged) acknowledge(purchase)
        }

        val owned = bought.isNotEmpty()
        prefs.edit().putBoolean(KEY_OWNED, owned).apply()
        Premium.setOwned(owned)
        when {
            owned -> Unit
            pending -> Premium.setMessage(PremiumMessage.PENDING)
            userAsked -> Premium.setMessage(PremiumMessage.NOTHING_TO_RESTORE)
        }
    }

    private fun acknowledge(purchase: Purchase) {
        val billing = client ?: return
        val params = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.purchaseToken).build()
        billing.acknowledgePurchase(params) { }
    }
}
