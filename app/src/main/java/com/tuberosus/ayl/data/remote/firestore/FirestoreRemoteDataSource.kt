package com.tuberosus.ayl.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.tuberosus.ayl.data.mapper.toAppError
import com.tuberosus.ayl.domain.util.Result
import kotlinx.coroutines.tasks.await

class FirestoreRemoteDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun <T> getCollection(
        collection: String,
        clazz: Class<T>
    ): Result<List<T>> {
        return try {
            val data = firestore.collection(collection)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(clazz) }
            Result.Success(data)
        } catch (e: Throwable) {
            Result.Failure(e.toAppError())
        }
    }
}

suspend inline fun <reified T> FirestoreRemoteDataSource.getCollection(
    collection: String,
): Result<List<T>> = getCollection(collection, T::class.java)