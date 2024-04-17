package org.example.task.event

data class ExternalTransactionEvent(
    val externalId: String,
    val status: ExternalTransactionStatus,
    val code: Long
)

enum class ExternalTransactionStatus {
    SUCCESS,
    DECLINE,
    ERROR
}