package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.util.Result

interface StaffRepository {
    suspend fun getStaff(): Result<List<Staff>>
}