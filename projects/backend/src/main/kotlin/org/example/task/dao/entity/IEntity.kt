package org.example.task.dao.entity

import jakarta.persistence.Column
import java.time.Instant
import java.time.OffsetDateTime

abstract class IEntity {

    @Column(name = "created_at")
    open val createdAt: OffsetDateTime = OffsetDateTime.now()

    @Column(name = "updated_at")
    open var updatedAt: OffsetDateTime = OffsetDateTime.now()
}
