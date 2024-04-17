package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGatePostback (

    @field:JsonProperty("transaction_id")
    val transactionId: String,

    @field:JsonProperty("transaction_type")
    val transactionType: UpGatePostbackTransactionType,

    @field:JsonProperty("transaction_status")
    val transactionStatus: UpGatePostbackTransactionStatus,

    /* UTC date time format */
    @field:JsonProperty("created_at")
    val createdAt: String,

    /* Code from [response codes](#section/Constants/Response-Codes)  */
    @field:JsonProperty("response_code")
    val responseCode: Long,

    /* Message from [response codes](#section/Constants/Response-Codes)  */
    @field:JsonProperty("response_text")
    val responseText: String,

    @field:JsonProperty("transaction_details")
    val transactionDetails: UpGateTransactionDetails,

    @field:JsonProperty("payment_details")
    val paymentDetails: UpGatePaymentDetails,

    @field:JsonProperty("payment")
    val payment: UpGatePayment,

    @field:JsonProperty("payment_context")
    val paymentContext: UpGatePaymentContext? = null

)

