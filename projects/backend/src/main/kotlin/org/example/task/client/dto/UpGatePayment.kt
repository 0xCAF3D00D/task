package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class UpGatePayment (

    @field:JsonProperty("payment_id")
    val paymentId: String,

    @field:JsonProperty("payment_type")
    val paymentType: UpGatePaymentPaymentType,

    @field:JsonProperty("payment_method")
    val paymentMethod: UpGatePaymentMethod,

    /* UTC date time format */
    @field:JsonProperty("created_at")
    val createdAt: String,

    @field:JsonProperty("merchant_id")
    val merchantId: String,

    @field:JsonProperty("merchant_customer_id")
    val merchantCustomerId: String,

    @field:JsonProperty("email")
    val email: String,

    @field:JsonProperty("amount")
    val amount: BigDecimal,

    /* Country Code (ISO 3166-2) */
    @field:JsonProperty("country_code")
    val countryCode: String,

    /* Currency code (ISO 4217) */
    @field:JsonProperty("currency_code")
    val currencyCode: String,

    /* Expected at least 1 product */
    @field:JsonProperty("products")
    val products: List<UpGateProduct>,

    @field:JsonProperty("merchant_payment_id")
    val merchantPaymentId: String? = null,

    @field:JsonProperty("language")
    val language: String? = null,

    @field:JsonProperty("base_amount")
    val baseAmount: String? = null,

    /* Currency code (ISO 4217) */
    @field:JsonProperty("base_currency_code")
    val baseCurrencyCode: String? = null,

    @field:JsonProperty("transaction_amount")
    val transactionAmount: String? = null,

    /* Currency code (ISO 4217) */
    @field:JsonProperty("transaction_currency_code")
    val transactionCurrencyCode: String? = null,

    /* If needs to initiate 3d secure from merchant side */
    @field:JsonProperty("forced_3d")
    val forced3d: Boolean? = null,

    /* **required** only for SALE | AUTHORIZE | RECURRING  */
    @field:JsonProperty("success_url")
    val successUrl: String? = null,

    /* **required** only for SALE | AUTHORIZE | RECURRING  */
    @field:JsonProperty("failure_url")
    val failureUrl: String? = null,

    /* Token from original payment. Used for MIT_SALE | MIT_AUTHORIZE  */
    @field:JsonProperty("payment_token_id")
    val paymentTokenId: String? = null,

    /* Shop name  */
    @field:JsonProperty("shop_name")
    val shopName: String? = null,

    /* Shop URL with protocol HTTP or HTTPS  */
    @field:JsonProperty("shop_url")
    val shopUrl: String? = null,

    @field:JsonProperty("session")
    val session: UpGateSession? = null

)

