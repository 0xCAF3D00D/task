package org.example.task.dao.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "purchases")
class Purchase(
    @Column(name = "email")
    val email: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    val product: Product,

    @Column(name = "amount")
    val amount: BigDecimal,

    @Column(name = "currency")
    val currency: String,
) : IEntity() {
    @Id
    val id: UUID = UUID.randomUUID()

    @Column(name = "status")
    var status: PurchaseStatus = PurchaseStatus.CREATED

    @Column(name = "external_id")
    var externalId: String? = null

    @Column(name = "external_url")
    var externalUrl: String? = null

    @Column(name = "code")
    var code: Long? = null
}

fun Purchase.transitTo(to: PurchaseStatus): Purchase {
    status = to
    updatedAt = OffsetDateTime.now()

    return this
}

enum class PurchaseStatus {
    CREATED,
    PENDING,
    DECLINE,
    SUCCESS,
    ERROR
}