package com.tuberosus.ayl.feature.staff.staff_list

sealed interface StaffListAction {
    data class OnTgClick(val telegram: String) : StaffListAction
    data object OnRetryClick : StaffListAction
}