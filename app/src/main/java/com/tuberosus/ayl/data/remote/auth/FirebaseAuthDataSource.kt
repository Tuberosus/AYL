package com.tuberosus.ayl.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tuberosus.ayl.data.mapper.toAppError
import com.tuberosus.ayl.data.remote.auth.exception.UserIsNullException
import com.tuberosus.ayl.domain.util.Result
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource(
    private val auth: FirebaseAuth
) {
    suspend fun signIn(
        email: String,
        password: String,
    ): Result<FirebaseUser> {
        return try {
            val firebaseUser = auth.signInWithEmailAndPassword(email, password)
                .await()
                .user ?: throw UserIsNullException()

            Result.Success(firebaseUser)
        } catch (e: Exception) {
            Result.Failure(e.toAppError())
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
}