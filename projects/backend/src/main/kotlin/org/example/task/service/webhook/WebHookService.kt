package org.example.task.service.webhook

import org.example.task.dao.entity.WebHook
import org.example.task.dao.entity.WebHookStatus
import org.example.task.dao.repository.WebHookRepository
import org.example.task.event.WebHookEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WebHookService(
    private val webHookRepository: WebHookRepository
) {

    @Transactional
    fun create(event: WebHookEvent, status: WebHookStatus) {
        webHookRepository.save(WebHook(
            payload = event.json,
            status = status
        ))
    }

}