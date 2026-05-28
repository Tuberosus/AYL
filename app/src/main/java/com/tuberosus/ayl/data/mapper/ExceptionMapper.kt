package com.tuberosus.ayl.data.mapper

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tuberosus.ayl.data.remote.auth.exception.UserIsNullException
import com.tuberosus.ayl.domain.util.AppError

fun Exception.toAppError(): AppError {
    return when (this) {
        is FirebaseAuthInvalidCredentialsException ->
            AppError.InvalidCredentials

        is FirebaseAuthInvalidUserException ->
            AppError.NotFound

        is UserIsNullException ->
            AppError.Unauthorized

        is FirebaseFirestoreException -> {
            when (code) {
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    AppError.Network

                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED ->
                    AppError.Timeout

                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    AppError.Permission

                FirebaseFirestoreException.Code.NOT_FOUND ->
                    AppError.NotFound

                else -> AppError.Unknown(message)
            }
        }

        else -> AppError.Unknown(message)
    }
}