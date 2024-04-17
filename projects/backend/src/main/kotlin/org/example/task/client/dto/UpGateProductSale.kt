package org.example.task.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class UpGateProductSale(
    @field:JsonProperty("product_price")
    override val productPrice: BigDecimal,

    @field:JsonProperty("product_name")
    override val productName: String,

    @field:JsonProperty("product_description")
    override val productDescription: String,

    /* Product ID in Upgate env */
    @field:JsonProperty("product_id")
    override val productId: String? = null,

    /* **Optional** field, might be used as a reference from the merchant side  */
    @field:JsonProperty("merchant_product_id")
    override val merchantProductId: String? = null,

    @field:JsonProperty("product_transaction_price")
    override val productTransactionPrice: BigDecimal? = null,

    @field:JsonProperty("product_type")
    override val productType: ProductType = ProductType.SALE,
) : UpGateProduct

