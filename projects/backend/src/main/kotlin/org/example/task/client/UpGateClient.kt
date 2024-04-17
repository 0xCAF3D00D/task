package org.example.task.client

import org.example.task.client.dto.UpGatePaymentRequest
import org.example.task.client.dto.UpGatePaymentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(name = "upgate-service")
interface UpGateClient {

    @PostMapping("/v1/sale")
    fun createPayment(request: UpGatePaymentRequest,
                      @RequestHeader(X_IDEMPOTENCY_KET) idempotencyKey: String? = null): UpGatePaymentResponse

    companion object {
        const val X_IDEMPOTENCY_KET = "X-Idempotency-Key"
    }

}
