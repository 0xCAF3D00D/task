package org.example.task.dao.repository

import org.example.task.dao.entity.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PurchaseRepository : JpaRepository<Purchase, UUID> {

    fun findByExternalId(externalId: String): Purchase?

}