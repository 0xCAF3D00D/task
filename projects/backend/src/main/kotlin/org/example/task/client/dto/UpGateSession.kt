package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGateSession (

    /* UTC date time format */
    @field:JsonProperty("created_at")
    val createdAt: String,

    /* UTC date time format */
    @field:JsonProperty("expires_at")
    val expiresAt: String,

    @field:JsonProperty("redirect_url")
    val redirectUrl: String

)

