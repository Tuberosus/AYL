package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toStaff
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.remote.firestore.dto.StaffDto
import com.tuberosus.ayl.data.remote.firestore.getCollection
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.repository.StaffRepository

class StaffRepositoryImpl(
    private val firestoreRemoteDataSource: FirestoreRemoteDataSource
) : StaffRepository {
    override suspend fun getStaff(): List<Staff> {
        return firestoreRemoteDataSource
            .getCollection<StaffDto>(STAFF_COLLECTION)
            .map { it.toStaff() }
    }

    companion object {
        private const val STAFF_COLLECTION = "Staff"
    }
}