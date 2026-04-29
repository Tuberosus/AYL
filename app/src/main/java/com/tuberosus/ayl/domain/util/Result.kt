package com.tuberosus.ayl.domain.util

sealed interface Result<out T> {
    data class Success<out T>(val data: T): Result<T>
    data class Failure(val error: AppError): Result<Nothing>
}

inline fun <T, R> Result<T>.map(map: (T) -> R): Result<R> {
    return when(this) {
        is Result.Failure -> Result.Failure(error)
        is Result.Success -> Result.Success(map(this.data))
    }
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    return when(this) {
        is Result.Failure -> this
        is Result.Success -> {
            action(this.data)
            this
        }
    }
}

inline fun <T> Result<T>.onFailure(action: (AppError) -> Unit): Result<T> {
    return when(this) {
        is Result.Failure -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}