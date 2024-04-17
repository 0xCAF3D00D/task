package org.example.task.service.provider

import org.example.task.dao.entity.Purchase

interface PaymentProvider {
    fun createPayment(purchase: Purchase, context: PurchaseContext): PaymentResult
}

data class PurchaseContext(
    val redirectUrl: String
)

sealed class PaymentResult(
    val success: Boolean,
) {
    data class Success(val externalId: String, val redirectUrl: String) : PaymentResult(true)
    data object Failure : PaymentResult(false)
}