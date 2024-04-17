package org.example.task.dao.repository

import org.example.task.dao.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, UUID>