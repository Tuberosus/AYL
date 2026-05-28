package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toUser
import com.tuberosus.ayl.data.remote.auth.FirebaseAuthDataSource
import com.tuberosus.ayl.domain.model.auth.User
import com.tuberosus.ayl.domain.repository.AuthRepository
import com.tuberosus.ayl.domain.util.Result
import com.tuberosus.ayl.domain.util.map

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User> {
        return authDataSource.signIn(
            email = email,
            password = password,
        ).map { firebaseUser -> firebaseUser.toUser() }
    }

    override fun getCurrentUser(): User? {
        return authDataSource.getCurrentUser()?.toUser()
    }

    override suspend fun signOut() {
        authDataSource.signOut()
    }
}