package org.example.task.controller.dto

import org.example.task.dao.entity.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductDto(
    val id: UUID,
    val price: BigDecimal,
    val currency: String,
    val name: String,
    val description: String
)

fun Product.toDto(): ProductDto {
    return ProductDto(
        id = this.id,
        price = this.price,
        currency = this.currency,
        name = this.name,
        description = this.description
    )
}