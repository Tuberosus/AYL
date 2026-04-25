package com.tuberosus.ayl.feature.staff.navigation

import com.tuberosus.ayl.navigation.Route
import kotlinx.serialization.Serializable

sealed interface StaffGraphRoutes : Route {
    @Serializable
    data object Graph : StaffGraphRoutes

    @Serializable
    data object StaffList : StaffGraphRoutes
}