package com.tuberosus.ayl.feature.staff.navigation

import kotlinx.serialization.Serializable

sealed interface StaffGraphRoutes {
    @Serializable
    data object Graph : StaffGraphRoutes

    @Serializable
    data object StaffList : StaffGraphRoutes
}