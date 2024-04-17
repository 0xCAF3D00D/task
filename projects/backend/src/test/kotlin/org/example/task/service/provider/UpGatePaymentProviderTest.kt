package org.example.task.service.provider

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.task.TaskApplication
import org.example.task.client.UpGateClient
import org.example.task.client.dto.UpGatePaymentResponse
import org.example.task.dao.entity.Product
import org.example.task.dao.entity.Purchase
import org.example.task.service.provider.upgate.UpGatePaymentProvider
import org.example.task.utils.ResourceUtils
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigurationPackage(basePackageClasses = [TaskApplication::class])
@ContextConfiguration(classes = [JacksonAutoConfiguration::class, UpGatePaymentProvider::class])
class UpGatePaymentProviderTest {
    @MockBean
    private lateinit var client: UpGateClient

    @Autowired
    private lateinit var paymentProvider: PaymentProvider

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val purchase = Purchase(
        amount = BigDecimal("100.00"),
        currency = "USD",
        email = "test@test.com",
        product = Product(
            name = "test product",
            description = "test description",
            price = BigDecimal("100.00"),
            currency = "USD"
        )
    )

    @Test
    fun `create payment success`() {
        val response = objectMapper.readValue<UpGatePaymentResponse>(ResourceUtils.getContentAsString("stubs/upgate/success.json"))

        whenever(client.createPayment(any(), any())).thenReturn(response)

        val result = paymentProvider.createPayment(purchase, PurchaseContext("http://localhost"))

        result should {
            it.success shouldBe true

            val success = it as PaymentResult.Success
            success.externalId shouldBe response.data.paymentId
            success.redirectUrl shouldBe response.data.session?.redirectUrl
        }

        verify(client).createPayment(any(), any())
        verifyNoMoreInteractions(client)
    }

    @Test
    fun `create payment failed`() {
        whenever(client.createPayment(any(), any())).thenThrow(IllegalStateException("Some exception"))

        val result = paymentProvider.createPayment(purchase, PurchaseContext("http://localhost"))
        result should {
            it.success shouldBe false
        }

        verify(client).createPayment(any(), any())
        verifyNoMoreInteractions(client)
    }

}