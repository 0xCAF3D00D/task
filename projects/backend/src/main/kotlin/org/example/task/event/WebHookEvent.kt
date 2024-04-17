package org.example.task.event

import com.fasterxml.jackson.databind.JsonNode

sealed class WebHookEvent(val json: JsonNode) {

    class UpGateTransactionPostback(json: JsonNode) : WebHookEvent(json)

    class Success(event: WebHookEvent) : WebHookEvent(event.json)
    class Failed(event: WebHookEvent) : WebHookEvent(event.json)
}

