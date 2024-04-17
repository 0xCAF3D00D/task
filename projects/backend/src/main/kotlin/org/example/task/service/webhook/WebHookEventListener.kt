package org.example.task.service.webhook

import org.example.task.dao.entity.WebHookStatus
import org.example.task.event.WebHookEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WebHookEventListener(
    private val webHookService: WebHookService
) {

    @Async
    @Transactional
    @EventListener
    fun onWebhookSuccess(event: WebHookEvent.Success) = handleEvent(event, WebHookStatus.SUCCESS)

    @Async
    @Transactional
    @EventListener
    fun onWebhookFailed(event: WebHookEvent.Failed) = handleEvent(event, WebHookStatus.ERROR)

    private fun handleEvent(event: WebHookEvent, status: WebHookStatus) {
        webHookService.create(event, status)
    }

}