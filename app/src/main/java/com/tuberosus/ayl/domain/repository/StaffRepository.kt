package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.staff.Staff

interface StaffRepository {
    suspend fun getStaff(): List<Staff>
}