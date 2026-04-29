package com.tuberosus.ayl.feature.staff.staff_list

import com.tuberosus.ayl.domain.model.staff.Staff

data class StaffListState(
    val isLoading: Boolean = true,
    val staff: List<Staff>? = null,
)