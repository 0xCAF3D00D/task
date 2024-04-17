package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGateTransactionDetails (

    @field:JsonProperty("processor")
    val processor: String,

    @field:JsonProperty("processor_response_code")
    val processorResponseCode: String,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_authentication_value")
    val threeDsAuthenticationValue: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_processor")
    val threeDsProcessor: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_server_transaction_id")
    val threeDsServerTransactionId: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_eci")
    val threeDsEci: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_status")
    val threeDsStatus: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_challenge_type")
    val threeDsChallengeType: String? = null,

    /* Only for 3ds postback */
    @field:JsonProperty("three_ds_version")
    val threeDsVersion: String? = null,

    @field:JsonProperty("processor_merchant_account_id")
    val processorMerchantAccountId: String? = null,

    @field:JsonProperty("processor_transaction_id")
    val processorTransactionId: String? = null,

    @field:JsonProperty("arn")
    val arn: String? = null,

    @field:JsonProperty("reference_transaction_id")
    val referenceTransactionId: String? = null

)

