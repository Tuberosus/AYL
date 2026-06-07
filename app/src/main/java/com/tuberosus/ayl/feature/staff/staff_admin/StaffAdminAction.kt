package com.tuberosus.ayl.feature.staff.staff_admin

import com.tuberosus.ayl.domain.model.staff.Staff

sealed interface StaffAdminAction {
    data class OnNameChange(val value: String) : StaffAdminAction
    data class OnPositionChange(val value: String) : StaffAdminAction
    data class OnPhotoUrlChange(val value: String) : StaffAdminAction
    data class OnTelegramLinkChange(val value: String) : StaffAdminAction
    data class OnBioChange(val value: String) : StaffAdminAction
    data object OnSave : StaffAdminAction
    data object OnDismiss : StaffAdminAction
    data class SetStaffForUpdate(val staff: Staff?) : StaffAdminAction
}