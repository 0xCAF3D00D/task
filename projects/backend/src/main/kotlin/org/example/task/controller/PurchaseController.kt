package org.example.task.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.task.controller.dto.*
import org.example.task.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "PurchaseController", description = "Purchase API")
@RequestMapping("/api/purchase")
@RestController
class PurchaseController(
    private val purchaseService: PurchaseService
) {

    @Operation(summary = "Create a purchase")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPurchase(@Valid @RequestBody body: PurchaseRequest): ApiDataResponse<PurchaseDto> {
        return purchaseService.createPurchase(body)
            .toDto()
            .toApiResponse()
    }

    @Operation(summary = "Get purchase by ID")
    @GetMapping("/{id}")
    fun getPurchase(@PathVariable id: UUID): ApiDataResponse<PurchaseDto> {
        return purchaseService.getPurchaseById(id)
            .toDto()
            .toApiResponse()
    }

}