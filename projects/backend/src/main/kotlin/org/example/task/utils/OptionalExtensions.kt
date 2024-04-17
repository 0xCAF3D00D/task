package org.example.task.utils

import org.example.task.exception.EntityNotFoundException

inline fun <reified T : Any> T?.orElseThrowNotFound(): T = this ?: throw EntityNotFoundException(
    message = "Entity not found: ${T::class.simpleName}"
)
