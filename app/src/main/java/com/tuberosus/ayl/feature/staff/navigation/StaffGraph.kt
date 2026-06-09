package com.tuberosus.ayl.feature.staff.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.feature.staff.staff_list.StaffListScreenRoot

fun NavGraphBuilder.staffGraph(
    isLoggedIn: Boolean,
    onStaffEdit: (Staff?) -> Unit,
) {
    navigation<StaffGraphRoutes.Graph>(
        startDestination = StaffGraphRoutes.StaffList
    ) {
        composable<StaffGraphRoutes.StaffList> {
            StaffListScreenRoot(
                isLoggedIn = isLoggedIn,
                onStaffEdit = onStaffEdit
            )
        }
    }
}