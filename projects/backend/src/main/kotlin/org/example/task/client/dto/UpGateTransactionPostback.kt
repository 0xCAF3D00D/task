package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGateTransactionPostback (

    @field:JsonProperty("type")
    val type: UpGateTransactionPostbackType,

    @field:JsonProperty("data")
    val `data`: UpGatePostback

)

