package com.tuberosus.ayl.feature.staff.staff_admin

import com.tuberosus.ayl.feature.staff.staff_admin.model.StaffDraft

data class StaffAdminState(
    val staffDraft: StaffDraft = StaffDraft(),
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
)