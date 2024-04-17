package org.example.task.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val status: HttpStatus,
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException()

class EntityNotFoundException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : ApiException(HttpStatus.NOT_FOUND, message, cause)
