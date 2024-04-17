package org.example.task.dao.entity

import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity
@Table(name = "webhooks")
class WebHook(
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    val payload: JsonNode,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: WebHookStatus
) : IEntity() {
    @Id
    val id: UUID = UUID.randomUUID()
}

enum class WebHookStatus {
    SUCCESS,
    ERROR
}