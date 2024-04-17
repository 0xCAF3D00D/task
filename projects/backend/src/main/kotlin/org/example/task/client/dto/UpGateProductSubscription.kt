package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class ProductSubscription(

    @field:JsonProperty("product_price")
    override val productPrice: BigDecimal,

    @field:JsonProperty("product_name")
    override val productName: String,

    @field:JsonProperty("product_description")
    override val productDescription: String,

    @field:JsonProperty("product_type")
    override val productType: ProductType = ProductType.SUBSCRIPTION,

    @field:JsonProperty("charge_interval_value")
    val chargeIntervalValue: Int,

    @field:JsonProperty("charge_interval")
    val chargeInterval: ProductSubscriptionInterval,

    /* If subscription with trial period */
    @field:JsonProperty("is_trial")
    val isTrial: Boolean,

    /* Product ID in Upgate env */
    @field:JsonProperty("product_id")
    override val productId: String? = null,

    /* **Optional** field, might be used as a reference from the merchant side  */
    @field:JsonProperty("merchant_product_id")
    override val merchantProductId: String? = null,

    @field:JsonProperty("product_transaction_price")
    override val productTransactionPrice: BigDecimal? = null,

    /* Only for trial subscription */
    @field:JsonProperty("trial_interval_value")
    val trialIntervalValue: Int? = null,

    @field:JsonProperty("trial_interval")
    val trialInterval: ProductSubscriptionInterval? = null

) : UpGateProduct

enum class ProductSubscriptionInterval {
    DAY
}