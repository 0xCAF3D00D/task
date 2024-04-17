package org.example.task.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.task.controller.dto.ApiDataResponse
import org.example.task.controller.dto.ProductDto
import org.example.task.controller.dto.toApiResponse
import org.example.task.controller.dto.toDto
import org.example.task.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "ProductController", description = "Product API")
@RequestMapping("/api/product")
@RestController
class ProductController(
    private val productService: ProductService
) {

    @Operation(summary = "Get product list")
    @GetMapping
    fun getProducts(): ApiDataResponse<List<ProductDto>> {
        return productService.getProducts()
            .map { it.toDto() }
            .toApiResponse()
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: UUID): ApiDataResponse<ProductDto> {
        return productService.getProductById(id)
            .toDto()
            .toApiResponse()
    }

}