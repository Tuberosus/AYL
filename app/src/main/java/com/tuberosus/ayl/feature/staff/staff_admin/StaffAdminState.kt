package com.tuberosus.ayl.feature.staff.staff_admin

data class StaffAdminState(
    val name: String = "",
    val position: String = "",
    val photoUrl: String = "",
    val telegramLink: String = "",
    val bio: String = "",
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
)