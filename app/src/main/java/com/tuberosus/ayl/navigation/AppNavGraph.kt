package com.tuberosus.ayl.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tuberosus.ayl.app.BottomSheetType
import com.tuberosus.ayl.feature.contacts.navigation.contactsGraph
import com.tuberosus.ayl.feature.gallery.navigation.galleryGraph
import com.tuberosus.ayl.feature.home.navigation.HomeGraphRoutes
import com.tuberosus.ayl.feature.home.navigation.homeGraph
import com.tuberosus.ayl.feature.news.navigation.newsGraph
import com.tuberosus.ayl.feature.staff.navigation.staffGraph

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit,
    onItemUpdate: (BottomSheetType?) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeGraphRoutes.Graph,
        modifier = modifier
    ) {
        homeGraph(
            navController = navHostController,
            onLoginClick = onLoginClick,
        )
        galleryGraph(isLoggedIn = isLoggedIn)
        newsGraph(
            isLoggedIn = isLoggedIn,
            onEditNews = { news ->
                onItemUpdate(
                    BottomSheetType.NewsType(news)
                )
            },
            navController = navHostController
        )
        staffGraph(
            isLoggedIn = isLoggedIn,
            onStaffEdit = { staff ->
                onItemUpdate(
                    BottomSheetType.StaffType(staff)
                )
            }
        )
        contactsGraph(navHostController)
    }
}