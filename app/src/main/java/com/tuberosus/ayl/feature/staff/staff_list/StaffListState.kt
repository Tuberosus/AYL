package com.tuberosus.ayl.feature.staff.staff_list

import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.util.AppError

data class StaffListState(
    val isLoading: Boolean = true,
    val staff: List<Staff>? = null,
    val error: AppError? = null,
    val isEditMenuOpen: Boolean = false,
    val selectedStaff: Staff? = null,
)