package com.tuberosus.ayl.feature.staff.staff_list

import com.tuberosus.ayl.domain.model.staff.Staff

sealed interface StaffListAction {
    data class OnTgClick(val telegram: String) : StaffListAction
    data class OnStaffLongClick(val staff: Staff) : StaffListAction
    data object OnDeleteClick : StaffListAction
    data object OnDismissClick : StaffListAction
}