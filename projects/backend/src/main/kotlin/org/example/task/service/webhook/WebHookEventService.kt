package org.example.task.service.webhook

import mu.KotlinLogging
import org.example.task.event.WebHookEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger { }

@Service
class WebHookEventService(
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun onEvent(event: WebHookEvent) {
        log.debug { "Received webhook event: $event"}

        // TODO validate signature

        runCatching {
            eventPublisher.publishEvent(event)
        }
            .onSuccess { eventPublisher.publishEvent(WebHookEvent.Success(event)) }
            .onFailure { eventPublisher.publishEvent(WebHookEvent.Failed(event)) }
            .getOrThrow()
    }


}