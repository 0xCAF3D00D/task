package org.example.task.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.example.task.dao.entity.Purchase
import org.example.task.dao.entity.PurchaseStatus
import java.math.BigDecimal
import java.util.UUID

data class PurchaseDto(
    val id: UUID,
    val status: PurchaseStatus,
    val amount: BigDecimal,
    val currency: String,
    val product: ProductDto,
    val url: String?,
)

fun Purchase.toDto(): PurchaseDto {
    return PurchaseDto(
        id = this.id,
        status = this.status,
        amount = this.amount,
        currency = this.currency,
        url = this.externalUrl,
        product = this.product.toDto()
    )
}