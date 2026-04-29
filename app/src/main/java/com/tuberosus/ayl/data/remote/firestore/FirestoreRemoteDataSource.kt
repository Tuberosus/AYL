package com.tuberosus.ayl.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRemoteDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun <T> getCollection(
        collection: String,
        clazz: Class<T>
    ): List<T> {
        return firestore.collection(collection)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(clazz) }
    }
}

suspend inline fun <reified T> FirestoreRemoteDataSource.getCollection(
    collection: String,
): List<T> = getCollection(collection, T::class.java)