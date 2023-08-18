package com.example.warriorsofhind.network

import retrofit2.Response

data class ApiResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {
    sealed class Status {
        object Success : Status()
        object Failure : Status()
        object Loading: Status()
    }

    val isFailed: Boolean
        get() = this.status == Status.Failure

    val isSucceed: Boolean
        get() = !isFailed && this.data?.isSuccessful == true

    val dataBody: T
        get() = this.data?.body()!!

    val bodyNullable: T?
        get() = this.data?.body()!!

    // Companion Object - access methods using class name without creating an instance of it
    companion object {
        fun <T> successResponse(data: Response<T>): ApiResponse<T> {
            return ApiResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failureResponse(exception: Exception?): ApiResponse<T> {
            return ApiResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }

    }
}