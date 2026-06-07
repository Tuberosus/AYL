package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface StaffRepository {
    fun observeStaff(): Flow<Result<List<Staff>>>
    suspend fun getStaff(): Result<List<Staff>>
    suspend fun saveStaff(staff: Staff): Result<Unit>
    suspend fun deleteStaff(staffId: String): Result<Unit>
}