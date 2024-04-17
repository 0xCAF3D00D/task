package org.example.task.service.provider.upgate

import mu.KotlinLogging
import org.example.task.client.UpGateClient
import org.example.task.client.dto.UpGatePaymentMethod
import org.example.task.client.dto.UpGatePaymentRequest
import org.example.task.dao.entity.Purchase
import org.example.task.service.provider.upgate.mapper.UpGateMapper.toProductSale
import org.example.task.service.provider.PaymentProvider
import org.example.task.service.provider.PaymentResult
import org.example.task.service.provider.PurchaseContext
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

private val log = KotlinLogging.logger { }

@Service
class UpGatePaymentProvider(
    private val upGateClient: UpGateClient
) : PaymentProvider {

    override fun createPayment(purchase: Purchase, context: PurchaseContext): PaymentResult {
        log.info { "Creating payment for purchase ${purchase.id} using UpGate" }

        val redirectUrl = UriComponentsBuilder.fromUriString(context.redirectUrl)
            .path(purchase.id.toString())
            .toUriString()

        return runCatching {
            val response = upGateClient.createPayment(
                request = UpGatePaymentRequest(
                    paymentMethod = UpGatePaymentMethod.CARD,
                    merchantCustomerId = UUID.randomUUID().toString(), // stub
                    email = purchase.email,
                    amount = purchase.amount,
                    language = "en",
                    successUrl = redirectUrl,
                    failureUrl = redirectUrl,
                    countryCode = "US",
                    currencyCode = purchase.currency,
                    products = listOf(purchase.product.toProductSale()),
                ),
                idempotencyKey = purchase.id.toString()
            )

            PaymentResult.Success(
                externalId = response.data.paymentId,
                redirectUrl = requireNotNull(response.data.session?.redirectUrl)
            )
        }
            .onFailure { log.error(it) { "Could not create payment" } }
            .getOrDefault(PaymentResult.Failure)
    }

}