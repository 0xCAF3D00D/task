package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGatePaymentResponse(

    @field:JsonProperty("type")
    val type: UpGatePaymentResponseType,

    @field:JsonProperty("data")
    val `data`: UpGatePayment

)

