package com.tuberosus.ayl.domain.util

sealed interface AppError {
    data object Network : AppError
    data object Timeout : AppError
    data object NotFound : AppError
    data object Permission : AppError
    data class Unknown(val message: String?) : AppError
    data object Unauthorized : AppError
    data object InvalidCredentials : AppError
}