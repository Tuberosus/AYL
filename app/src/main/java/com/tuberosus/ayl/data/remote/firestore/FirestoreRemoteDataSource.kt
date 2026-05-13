package com.tuberosus.ayl.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.tuberosus.ayl.data.mapper.toAppError
import com.tuberosus.ayl.data.remote.firestore.dto.FirestoreDocument
import com.tuberosus.ayl.domain.util.AppError
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
            val snapshot = firestore.collection(collection)
                .get()
                .await()

            val data = snapshot.documents
                .mapNotNull { document ->
                    val item = document.toObject(clazz)
                    if (item is FirestoreDocument) {
                        item.id = document.id
                    }
                    item
                }

            if (snapshot.metadata.isFromCache && data.isEmpty()) {
                return Result.Failure(AppError.Network)
            }

            Result.Success(data)

        } catch (e: Throwable) {
            Result.Failure(e.toAppError())
        }
    }

    suspend fun <T> getDocument(
        collection: String,
        documentId: String,
        clazz: Class<T>,
    ): Result<T> {
        return try {
            val snapshot = firestore
                .collection(collection)
                .document(documentId)
                .get()
                .await()

            val data = snapshot.toObject(clazz)

            if (data != null) {
                Result.Success(data)
            } else {
                Result.Failure(AppError.NotFound)
            }

        } catch (e: Exception) {
            Result.Failure(e.toAppError())
        }
    }
}

suspend inline fun <reified T> FirestoreRemoteDataSource.getCollection(
    collection: String,
): Result<List<T>> = getCollection(collection, T::class.java)

suspend inline fun <reified T> FirestoreRemoteDataSource.getDocument(
    collection: String,
    documentId: String,
): Result<T> = getDocument(
    collection = collection,
    documentId = documentId,
    clazz = T::class.java
)