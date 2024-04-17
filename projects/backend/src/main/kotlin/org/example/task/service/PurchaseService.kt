package org.example.task.service

import mu.KotlinLogging
import org.example.task.controller.dto.PurchaseRequest
import org.example.task.dao.entity.Purchase
import org.example.task.dao.entity.PurchaseStatus
import org.example.task.dao.entity.transitTo
import org.example.task.dao.repository.PurchaseRepository
import org.example.task.event.ExternalTransactionEvent
import org.example.task.event.ExternalTransactionStatus
import org.example.task.service.provider.PaymentProvider
import org.example.task.service.provider.PaymentResult
import org.example.task.service.provider.PurchaseContext
import org.example.task.utils.orElseThrowNotFound
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val log = KotlinLogging.logger { }

@Service
class PurchaseService(
    private val productService: ProductService,
    private val purchaseRepository: PurchaseRepository,
    private val paymentProvider: PaymentProvider
) {

    @Transactional(readOnly = true)
    fun getPurchaseById(id: UUID): Purchase {
        return purchaseRepository.findById(id)
            .orElse(null)
            .orElseThrowNotFound()
    }

    @Transactional
    fun createPurchase(request: PurchaseRequest): Purchase {
        log.info { "Creating purchase for product ${request.productId}" }

        val product = productService.getProductById(request.productId)
            .orElseThrowNotFound()

        val purchase = Purchase(
            email = request.email,
            product = product,
            amount = product.price,
            currency = product.currency
        )

        when (val result = paymentProvider.createPayment(purchase, PurchaseContext(request.redirectUrl))) {
            is PaymentResult.Success -> {
                purchase.externalId = result.externalId
                purchase.externalUrl = result.redirectUrl
                purchase.transitTo(PurchaseStatus.PENDING)

                log.info { "Purchase created with external ID ${result.externalId}" }
            }

            is PaymentResult.Failure -> {
                purchase.transitTo(PurchaseStatus.ERROR)

                log.warn { "Failed to create purchase" }
            }
        }

        return purchaseRepository.save(purchase)
    }

    @Transactional
    @EventListener
    fun onTransactionEvent(event: ExternalTransactionEvent) {
        val purchase = purchaseRepository.findByExternalId(event.externalId)
            .orElseThrowNotFound()

        // TODO some validation?

        val newStatus = event.status.toPurchaseStatus()

        log.info { "Updating purchase [${purchase.id}] from status ${purchase.status} to $newStatus" }
        log.info { "External purchase [${purchase.id}] response code: ${event.code}" }

        purchase.transitTo(newStatus)
        purchase.code = event.code

        purchaseRepository.save(purchase)
    }

    private fun ExternalTransactionStatus.toPurchaseStatus(): PurchaseStatus {
        return when (this) {
            ExternalTransactionStatus.SUCCESS -> PurchaseStatus.SUCCESS
            ExternalTransactionStatus.DECLINE -> PurchaseStatus.DECLINE
            ExternalTransactionStatus.ERROR -> PurchaseStatus.ERROR
        }
    }

}