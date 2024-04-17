package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpGatePaymentContext (

    @field:JsonProperty("browser_user_agent")
    val browserUserAgent: String? = null,

    @field:JsonProperty("browser_screen_height")
    val browserScreenHeight: String? = null,

    @field:JsonProperty("browser_color_depth")
    val browserColorDepth: String? = null,

    @field:JsonProperty("browser_language")
    val browserLanguage: String? = null,

    @field:JsonProperty("browser_timezone_offset")
    val browserTimezoneOffset: String? = null,

    @field:JsonProperty("browser_screen_width")
    val browserScreenWidth: String? = null,

    @field:JsonProperty("challenge_window_size")
    val challengeWindowSize: String? = null,

    @field:JsonProperty("browser_has_js_enabled")
    val browserHasJsEnabled: String? = null,

    @field:JsonProperty("browser_has_java_enabled")
    val browserHasJavaEnabled: String? = null,

    @field:JsonProperty("browser_accept_header")
    val browserAcceptHeader: String? = null,

    @field:JsonProperty("ip")
    val ip: String? = null

)

