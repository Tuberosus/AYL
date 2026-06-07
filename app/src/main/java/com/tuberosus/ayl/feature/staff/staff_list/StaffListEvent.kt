package com.tuberosus.ayl.feature.staff.staff_list

sealed interface StaffListEvent {
    data class OnTgClick(val telegram: String) : StaffListEvent
    data class InfoMessage(val message: String) : StaffListEvent
}