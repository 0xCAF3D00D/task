package org.example.task.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import org.example.task.validation.ValidationConstants
import java.util.UUID

data class PurchaseRequest(
    @field:Schema(description = "Product ID")
    val productId: UUID,
    @field:Schema(description = "Customer email")
    @field:Email(message = "Email is not valid", regexp = ValidationConstants.EMAIL_REGEXP)
    val email: String,
    @field:Schema(description = "Redirect URL after the purchase is completed")
    val redirectUrl: String,
)