package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "product_type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = UpGateProductSale::class, name = "SALE"),
    JsonSubTypes.Type(value = ProductSubscription::class, name = "SUBSCRIPTION")
)
interface UpGateProduct {

    @get:JsonProperty("product_price")
    val productPrice: BigDecimal

    @get:JsonProperty("product_name")
    val productName: String

    @get:JsonProperty("product_description")
    val productDescription: String

    /* Product ID in Upgate env */
    @get:JsonProperty("product_id")
    val productId: String?

    /* **Optional** field, might be used as a reference from the merchant side  */
    @get:JsonProperty("merchant_product_id")
    val merchantProductId: String?

    @get:JsonProperty("product_transaction_price")
    val productTransactionPrice: BigDecimal?

    @get:JsonProperty("product_type")
    val productType: ProductType
}

enum class ProductType {
    SALE,
    SUBSCRIPTION
}