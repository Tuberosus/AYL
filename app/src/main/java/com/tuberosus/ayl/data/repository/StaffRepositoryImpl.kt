package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toStaff
import com.tuberosus.ayl.data.mapper.toStaffDto
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.remote.firestore.dto.StaffDto
import com.tuberosus.ayl.data.remote.firestore.getCollection
import com.tuberosus.ayl.data.remote.firestore.observeCollection
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.repository.StaffRepository
import com.tuberosus.ayl.domain.util.Result
import com.tuberosus.ayl.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StaffRepositoryImpl(
    private val firestoreRemoteDataSource: FirestoreRemoteDataSource
) : StaffRepository {
    override fun observeStaff(): Flow<Result<List<Staff>>> {
        return firestoreRemoteDataSource
            .observeCollection<StaffDto>(STAFF_COLLECTION)
            .map { result ->
                result.map { list -> list.map { it.toStaff() } }
            }
    }

    override suspend fun getStaff(): Result<List<Staff>> {
        return firestoreRemoteDataSource
            .getCollection<StaffDto>(STAFF_COLLECTION)
            .map { list -> list.map { it.toStaff() } }
    }

    override suspend fun saveStaff(staff: Staff): Result<Unit> {
        return firestoreRemoteDataSource.saveDocument(
            collection = STAFF_COLLECTION,
            data = staff.toStaffDto()
        )
    }

    companion object {
        private const val STAFF_COLLECTION = "Staff"
    }
}