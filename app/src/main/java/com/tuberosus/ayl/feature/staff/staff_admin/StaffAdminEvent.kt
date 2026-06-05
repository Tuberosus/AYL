package com.tuberosus.ayl.feature.staff.staff_admin

sealed interface StaffAdminEvent {
    data object SuccessSave : StaffAdminEvent
    data class SaveErrorMessage(val message: String) : StaffAdminEvent
}