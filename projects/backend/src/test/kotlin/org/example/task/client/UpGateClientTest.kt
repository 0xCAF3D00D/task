package org.example.task.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import feign.FeignException
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.example.task.client.dto.UpGatePaymentMethod
import org.example.task.client.dto.UpGatePaymentPaymentType
import org.example.task.client.dto.UpGatePaymentRequest
import org.example.task.client.dto.UpGatePaymentResponseType
import org.example.task.utils.ResourceUtils.getContentAsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import java.math.BigDecimal

@AutoConfigureWireMock(port = 0)
@TestPropertySource(
    properties = [
        "spring.cloud.openfeign.client.config.upgate-service.url=http://localhost:\${wiremock.server.port}"
    ]
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableFeignClients(clients = [UpGateClient::class])
@ContextConfiguration(
    classes = [
        JacksonAutoConfiguration::class,
        HttpMessageConvertersAutoConfiguration::class,
        FeignAutoConfiguration::class]
)
class UpGateClientTest {

    @Autowired
    lateinit var client: UpGateClient

    @Autowired
    lateinit var wireMockServer: WireMockServer

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `create payment success`() {
        val request = objectMapper.readValue<UpGatePaymentRequest>(getContentAsString("stubs/upgate/request.json"))

        wireMockServer.stubFor(
            post(urlPathEqualTo("/v1/sale"))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getContentAsString("stubs/upgate/success.json"))
                )
        )

        val response = client.createPayment(request)

        response should {
            it.type shouldBe UpGatePaymentResponseType.PAYMENT
            it.data.paymentId shouldBe "2E2CL5R3KC7K3"
            it.data.paymentType shouldBe UpGatePaymentPaymentType.SALE
            it.data.paymentMethod shouldBe UpGatePaymentMethod.CARD
            it.data.amount shouldBe BigDecimal("9.99")
        }
    }

    @Test
    fun `create payment failed`() {
        val request = objectMapper.readValue<UpGatePaymentRequest>(getContentAsString("stubs/upgate/request.json"))

        wireMockServer.stubFor(
            post(urlPathEqualTo("/v1/sale"))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getContentAsString("stubs/upgate/failed.json"))
                )
        )

        assertThrows<FeignException> { client.createPayment(request) }
    }

}