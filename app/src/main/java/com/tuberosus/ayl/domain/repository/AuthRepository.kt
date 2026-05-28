package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.auth.User
import com.tuberosus.ayl.domain.util.Result

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Result<User>

    fun getCurrentUser(): User?

    suspend fun signOut()
}