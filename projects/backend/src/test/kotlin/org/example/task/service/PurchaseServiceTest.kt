package org.example.task.service

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.task.TaskApplication
import org.example.task.controller.dto.PurchaseRequest
import org.example.task.dao.entity.Product
import org.example.task.dao.entity.Purchase
import org.example.task.dao.entity.PurchaseStatus
import org.example.task.dao.repository.PurchaseRepository
import org.example.task.event.ExternalTransactionEvent
import org.example.task.event.ExternalTransactionStatus
import org.example.task.exception.EntityNotFoundException
import org.example.task.service.provider.PaymentProvider
import org.example.task.service.provider.PaymentResult
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigurationPackage(basePackageClasses = [TaskApplication::class])
@ContextConfiguration(classes = [PurchaseService::class])
class PurchaseServiceTest {

    @Autowired
    private lateinit var purchaseService: PurchaseService

    @MockBean
    private lateinit var productService: ProductService

    @MockBean
    private lateinit var purchaseRepository: PurchaseRepository

    @MockBean
    private lateinit var paymentProvider: PaymentProvider

    private val purchaseRequest = PurchaseRequest(
        productId = UUID.fromString("66c1e25b-1ef2-4663-b3b0-fb449a6f823a"),
        email = "test@test.com",
        redirectUrl = "http://localhost"
    )

    private val product = Product(
        name = "test product",
        description = "test description",
        price = BigDecimal("100.00"),
        currency = "USD"
    )

    private val purchase = Purchase(
        amount = BigDecimal("100.00"),
        currency = "USD",
        email = "test@test.com",
        product = product
    )

    @Test
    fun `create purchase - product not found`() {
        whenever(productService.getProductById(purchaseRequest.productId)).thenReturn(null)

        assertThrows<EntityNotFoundException> {
            purchaseService.createPurchase(purchaseRequest)
        }

        verify(productService).getProductById(purchaseRequest.productId)
        verifyNoMoreInteractions(productService, purchaseRepository, paymentProvider)
    }

    @Test
    fun `create purchase - success`() {
        val externalId = UUID.randomUUID().toString()

        whenever(productService.getProductById(purchaseRequest.productId)).thenReturn(product)
        whenever(paymentProvider.createPayment(any(), any())).thenReturn(
            PaymentResult.Success(externalId, "url"))
        whenever(purchaseRepository.save(any<Purchase>())).thenAnswer { it.arguments[0] }

        val response = purchaseService.createPurchase(purchaseRequest)

        val argumentCaptor = argumentCaptor<Purchase>()

        verify(productService).getProductById(purchaseRequest.productId)
        verify(paymentProvider).createPayment(any(), any())
        verify(purchaseRepository).save(argumentCaptor.capture())

        val purchase = argumentCaptor.lastValue

        response shouldBeEqual purchase
        purchase should {
            it.email shouldBe purchaseRequest.email
            it.product shouldBe product
            it.amount shouldBe product.price
            it.currency shouldBe product.currency
            it.externalId shouldBe externalId
            it.externalUrl shouldBe "url"
            it.status shouldBe PurchaseStatus.PENDING
        }

        verifyNoMoreInteractions(productService, purchaseRepository, paymentProvider)
    }

    @Test
    fun `create purchase - failed`() {
        whenever(productService.getProductById(purchaseRequest.productId)).thenReturn(product)
        whenever(paymentProvider.createPayment(any(), any())).thenReturn(
            PaymentResult.Failure)
        whenever(purchaseRepository.save(any<Purchase>())).thenAnswer { it.arguments[0] }

        val response = purchaseService.createPurchase(purchaseRequest)

        val argumentCaptor = argumentCaptor<Purchase>()

        verify(productService).getProductById(purchaseRequest.productId)
        verify(paymentProvider).createPayment(any(), any())
        verify(purchaseRepository).save(argumentCaptor.capture())

        val purchase = argumentCaptor.lastValue

        response shouldBeEqual purchase
        purchase should {
            it.email shouldBe purchaseRequest.email
            it.product shouldBe product
            it.amount shouldBe product.price
            it.currency shouldBe product.currency
            it.externalId shouldBe null
            it.externalUrl shouldBe null
            it.status shouldBe PurchaseStatus.ERROR
        }

        verifyNoMoreInteractions(productService, purchaseRepository, paymentProvider)
    }

    @Test
    fun `external transaction event - success`() {
        val externalId = UUID.randomUUID().toString()
        val event = ExternalTransactionEvent(
            externalId = externalId,
            status = ExternalTransactionStatus.SUCCESS,
            code = 200
        )

        whenever(purchaseRepository.findByExternalId(externalId)).thenReturn(purchase)
        whenever(purchaseRepository.save(any<Purchase>())).thenAnswer { it.arguments[0] }

        purchaseService.onTransactionEvent(event)

        verify(purchaseRepository).findByExternalId(externalId)

        val argumentCaptor = argumentCaptor<Purchase>()

        verify(purchaseRepository).save(argumentCaptor.capture())

        val purchase = argumentCaptor.lastValue

        purchase should {
            it.status shouldBe PurchaseStatus.SUCCESS
            it.code shouldBe 200
        }
    }

    @Test
    fun `external transaction event - declined`() {
        val externalId = UUID.randomUUID().toString()
        val event = ExternalTransactionEvent(
            externalId = externalId,
            status = ExternalTransactionStatus.DECLINE,
            code = 1337
        )

        whenever(purchaseRepository.findByExternalId(externalId)).thenReturn(purchase)
        whenever(purchaseRepository.save(any<Purchase>())).thenAnswer { it.arguments[0] }

        purchaseService.onTransactionEvent(event)

        verify(purchaseRepository).findByExternalId(externalId)

        val argumentCaptor = argumentCaptor<Purchase>()

        verify(purchaseRepository).save(argumentCaptor.capture())

        val purchase = argumentCaptor.lastValue

        purchase should {
            it.status shouldBe PurchaseStatus.DECLINE
            it.code shouldBe 1337
        }
    }


}