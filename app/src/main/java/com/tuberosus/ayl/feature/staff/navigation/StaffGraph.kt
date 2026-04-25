package com.tuberosus.ayl.feature.staff.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.staff.staff_list.StaffListScreenRoot

fun NavGraphBuilder.staffGraph() {
    navigation<StaffGraphRoutes.Graph>(
        startDestination = StaffGraphRoutes.StaffList
    ) {
        composable<StaffGraphRoutes.StaffList> {
            StaffListScreenRoot()
        }
    }
}