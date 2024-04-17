package org.example.task.service

import org.example.task.dao.entity.Product
import org.example.task.dao.repository.ProductRepository
import org.example.task.utils.orElseThrowNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    @Transactional(readOnly = true)
    fun getProducts(): List<Product> {
        return productRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getProductById(id: UUID): Product {
        return productRepository.findById(id)
            .orElse(null)
            .orElseThrowNotFound()
    }

}