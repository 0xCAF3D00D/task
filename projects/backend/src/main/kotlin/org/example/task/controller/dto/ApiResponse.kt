package org.example.task.controller.dto

import io.swagger.v3.oas.annotations.media.Schema

interface ApiResponse {
    val status: ApiResponseStatus
}

enum class ApiResponseStatus {
    OK,
    ERROR
}

@Schema(description = "Successful API response")
open class ApiResponseSuccess(
    @Schema(description = "Status of the operation")
    override val status: ApiResponseStatus = ApiResponseStatus.OK
) : ApiResponse

@Schema(description = "API response with an error message")
class ApiResponseError(
    @Schema(description = "Status of the operation")
    override val status: ApiResponseStatus = ApiResponseStatus.ERROR,
    @Schema(description = "Error message")
    val error: String? = null,
) : ApiResponse

@Schema(description = "Successful API response")
open class ApiDataResponse<T>(
    val data: T,
) : ApiResponseSuccess()

fun <T> T.toApiResponse(): ApiDataResponse<T> = ApiDataResponse(this)