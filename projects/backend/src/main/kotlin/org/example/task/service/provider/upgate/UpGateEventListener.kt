package org.example.task.service.provider.upgate

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import mu.KotlinLogging
import org.example.task.client.dto.UpGatePostbackTransactionType
import org.example.task.client.dto.UpGateTransactionPostback
import org.example.task.event.ExternalTransactionEvent
import org.example.task.event.WebHookEvent
import org.example.task.service.provider.upgate.mapper.UpGateMapper.toExternalTransactionStatus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger { }

@Service
class UpGateEventListener(
    private val eventPublisher: ApplicationEventPublisher,
    private val objectMapper: ObjectMapper
) {

    @EventListener
    fun onTransactionEvent(event: WebHookEvent.UpGateTransactionPostback) {
        val payload = objectMapper.treeToValue<UpGateTransactionPostback>(event.json)
        val postback = payload.data

        when (postback.transactionType) {
            UpGatePostbackTransactionType.SALE -> {
                eventPublisher.publishEvent(
                    ExternalTransactionEvent(
                        externalId = postback.payment.paymentId,
                        status = postback.transactionStatus.toExternalTransactionStatus(),
                        code = postback.responseCode
                    )
                )
            }

            else -> log.warn { "Unexpected upgate postback transaction type [${postback.transactionId}]. Skipping..." }
        }
    }

}