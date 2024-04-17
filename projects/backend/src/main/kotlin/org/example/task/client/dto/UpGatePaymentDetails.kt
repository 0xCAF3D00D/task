package org.example.task.client.dto


import com.fasterxml.jackson.annotation.JsonProperty

data class UpGatePaymentDetails (

    /* Only for **CARD** flow */
    @field:JsonProperty("payment_token_id")
    val paymentTokenId: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_scheme")
    val cardScheme: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_token_id")
    val cardTokenId: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_bin")
    val cardBin: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_last_four_digits")
    val cardLastFourDigits: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("customer_full_name")
    val customerFullName: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_expiry_month")
    val cardExpiryMonth: String? = null,

    /* Only for **CARD** flow */
    @field:JsonProperty("card_expiry_year")
    val cardExpiryYear: String? = null

)

