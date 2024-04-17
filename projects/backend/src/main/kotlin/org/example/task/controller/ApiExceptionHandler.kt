package org.example.task.controller

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import jakarta.servlet.ServletException
import mu.KotlinLogging
import org.example.task.controller.dto.ApiResponseError
import org.example.task.exception.ApiException
import org.example.task.exception.EntityNotFoundException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

private val log = KotlinLogging.logger { }

@RestControllerAdvice
class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleNotFoundException(ex: EntityNotFoundException): ResponseEntity<Any> =
        apiErrorResponseEntity(ex)

    @ExceptionHandler(value = [ApiException::class])
    protected fun handleApiException(ex: ApiException): ResponseEntity<Any> =
        apiErrorResponseEntity(ex)

    @ExceptionHandler(Throwable::class)
    protected fun handleUnknownException(e: Throwable): ResponseEntity<*> {
        return errorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, "Internal server error")
    }

    @ExceptionHandler(TypeMismatchException::class)
    fun handleTypeMismatch(
        e: TypeMismatchException,
    ): ResponseEntity<Any> = errorResponseEntity(
        status = HttpStatus.BAD_REQUEST,
        loggedEx = e,
        error = "Wrong path or query param format. Required type: ${e.requiredType?.name}"
    )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
    ): ResponseEntity<Any>? = when (val cause = e.cause) {
        is UnrecognizedPropertyException -> errorResponseEntity(
            status = HttpStatus.BAD_REQUEST,
            loggedEx = cause,
            error = "Unrecognized property: '${cause.propertyName}'"
        )

        else -> errorResponseEntity(
            status = HttpStatus.BAD_REQUEST,
            loggedEx = cause ?: e,
            error = "Bad request"
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
    ): ResponseEntity<Any>? = errorResponseEntity(
        status = HttpStatus.BAD_REQUEST,
        loggedEx = e,
        error = "Bad request, errors: ${
            e.bindingResult.fieldErrors.joinToString(
                prefix = "['",
                postfix = "']",
                separator = "', '"
            ) { it.defaultMessage ?: "" }
        }"
    )

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        e: HttpRequestMethodNotSupportedException,
    ): ResponseEntity<Any>? = errorResponseEntity(
        status = HttpStatus.BAD_REQUEST,
        loggedEx = e,
        error = e.message
    )

    @ExceptionHandler(value = [NoResourceFoundException::class, NoHandlerFoundException::class])
    fun handleNoHandlerFoundException(
        e: ServletException,
    ): ResponseEntity<Any>? = errorResponseEntity(
        status = HttpStatus.NOT_FOUND,
        loggedEx = e,
        error = e.message
    )

    private fun apiErrorResponseEntity(
        apiException: ApiException
    ): ResponseEntity<Any> {
        log(apiException)
        return ResponseEntity.status(apiException.status)
            .body(
                ApiResponseError(
                    error = apiException.message,
                )
            )
    }

    private fun errorResponseEntity(
        status: HttpStatus,
        loggedEx: Throwable,
        error: String? = null
    ): ResponseEntity<Any> {
        log(loggedEx)
        return ResponseEntity.status(status)
            .body(
                ApiResponseError(
                    error = error,
                )
            )
    }

    private fun log(throwable: Throwable) {
        log.warn { "Handling ${throwable::class.java.simpleName}, message: ${throwable.message}" }
    }
}