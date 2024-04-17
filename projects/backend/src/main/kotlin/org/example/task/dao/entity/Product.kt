package org.example.task.dao.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "products")
class Product(
    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "price")
    val price: BigDecimal,

    @Column(name = "currency")
    val currency: String
) : IEntity() {
    @Id
    val id: UUID = UUID.randomUUID()
}