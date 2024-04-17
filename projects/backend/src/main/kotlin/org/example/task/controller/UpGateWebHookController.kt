package org.example.task.controller

import com.fasterxml.jackson.databind.JsonNode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.task.event.WebHookEvent
import org.example.task.service.webhook.WebHookEventService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "UpGateWebHookController", description = "UpGate WebHook API")
@RequestMapping("/api/webhook/upgate")
@RestController
class UpGateWebHookController(
    private val webhookEventService: WebHookEventService,
) {

    @Operation(summary = "UpGate transaction postback")
    @PostMapping("/transaction")
    fun onWebhook(@RequestBody body: JsonNode) {
        webhookEventService.onEvent(WebHookEvent.UpGateTransactionPostback(body))
    }

}