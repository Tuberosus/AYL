package com.tuberosus.ayl.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tuberosus.ayl.feature.admin.AuthAction
import com.tuberosus.ayl.feature.admin.AuthScreenRoot
import com.tuberosus.ayl.feature.admin.AuthViewModel
import com.tuberosus.ayl.feature.gallery.gallery_admin.GalleryAdminRoot
import com.tuberosus.ayl.feature.gallery.navigation.GalleryGraphRoutes
import com.tuberosus.ayl.feature.news.navigation.NewsGraphRoutes
import com.tuberosus.ayl.feature.news.news_admin.NewsAdminScreenRoot
import com.tuberosus.ayl.feature.staff.navigation.StaffGraphRoutes
import com.tuberosus.ayl.feature.staff.staff_admin.StaffAdminRoot
import com.tuberosus.ayl.navigation.AppBottomBar
import com.tuberosus.ayl.navigation.AppNavGraph
import com.tuberosus.ayl.ui.components.layouts.AdminBottomSheet
import com.tuberosus.ayl.ui.components.topbars.AdminTopBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot(
    authViewModel: AuthViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val authState by authViewModel.state.collectAsStateWithLifecycle()

    val showAdminTopBar =
//        authState.isLoggedIn &&
        isRouteRequiringAdmin(currentDestination)

    var showDialog by rememberSaveable { mutableStateOf<BottomSheetType?>(null) }


    Scaffold(
        bottomBar = {
            AppBottomBar(navController)
        },
        topBar = {
            if (showAdminTopBar) {
                AdminTopBar(
                    onExitClick = authViewModel::signOut,
                    onAddClick = {
                        showDialog = when (currentAdminScreen(currentDestination?.route)) {
                            AdminScreen.GALLERY -> BottomSheetType.GALLERY
                            AdminScreen.NEWS -> BottomSheetType.NEWS
                            AdminScreen.STAFF -> BottomSheetType.STAFF
                            null -> null
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navController,
            onLoginClick = {
                showDialog = BottomSheetType.AUTH
            },
            modifier = Modifier.padding(paddingValues)
        )
    }

    if (showDialog != null) {
        AdminBottomSheet(
            onDismiss = {
                authViewModel.onAction(AuthAction.ClearInput)
                showDialog = null
            }
        ) {
            when (showDialog) {
                BottomSheetType.GALLERY ->
                    GalleryAdminRoot(onDismiss = { showDialog = null })

                BottomSheetType.NEWS ->
                    NewsAdminScreenRoot(onDismiss = { showDialog = null })

                BottomSheetType.STAFF ->
                    StaffAdminRoot(onDismiss = { showDialog = null })

                BottomSheetType.AUTH ->
                    AuthScreenRoot(
                        viewModel = authViewModel,
                        onDismiss = {
                            authViewModel.onAction(AuthAction.ClearInput)
                            showDialog = null
                        }
                    )

                else -> Unit
            }
        }
    }
}

private fun isRouteRequiringAdmin(currentDestination: NavDestination?): Boolean {
    val adminDestinations = setOf(
        GalleryGraphRoutes.GalleryList::class.qualifiedName,
        NewsGraphRoutes.NewsList::class.qualifiedName,
        StaffGraphRoutes.StaffList::class.qualifiedName
    )
    return currentDestination?.route in adminDestinations
}

private fun currentAdminScreen(route: String?): AdminScreen? {
    return when (route) {
        GalleryGraphRoutes.GalleryList::class.qualifiedName -> AdminScreen.GALLERY
        NewsGraphRoutes.NewsList::class.qualifiedName -> AdminScreen.NEWS
        StaffGraphRoutes.StaffList::class.qualifiedName -> AdminScreen.STAFF
        else -> null
    }
}

enum class BottomSheetType {
    GALLERY,
    NEWS,
    STAFF,
    AUTH,
}

enum class AdminScreen {
    GALLERY,
    NEWS,
    STAFF
}