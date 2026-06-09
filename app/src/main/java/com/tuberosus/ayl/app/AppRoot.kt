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
import com.tuberosus.ayl.feature.admin.AuthViewModel
import com.tuberosus.ayl.feature.gallery.navigation.GalleryGraphRoutes
import com.tuberosus.ayl.feature.news.navigation.NewsGraphRoutes
import com.tuberosus.ayl.feature.staff.navigation.StaffGraphRoutes
import com.tuberosus.ayl.navigation.AppBottomBar
import com.tuberosus.ayl.navigation.AppNavGraph
import com.tuberosus.ayl.ui.components.bottomsheets.AppRootBottomSheet
import com.tuberosus.ayl.ui.components.bottomsheets.BottomSheetType
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
        authState.isLoggedIn &&
        isRouteRequiringAdmin(currentDestination)

    var showDialogType by rememberSaveable { mutableStateOf<BottomSheetType?>(null) }


    Scaffold(
        bottomBar = {
            AppBottomBar(navController)
        },
        topBar = {
            if (showAdminTopBar) {
                AdminTopBar(
                    onExitClick = authViewModel::signOut,
                    onAddClick = {
                        showDialogType = when (currentAdminScreen(currentDestination?.route)) {
                            AdminScreen.GALLERY -> BottomSheetType.GalleryType()
                            AdminScreen.NEWS -> BottomSheetType.NewsType()
                            AdminScreen.STAFF -> BottomSheetType.StaffType()
                            null -> null
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navController,
            isLoggedIn = authState.isLoggedIn,
            onLoginClick = {
                showDialogType = BottomSheetType.Auth
            },
            onItemUpdate = { bottomSheetType ->
                showDialogType = bottomSheetType
            },
            modifier = Modifier.padding(paddingValues)
        )
    }

    AppRootBottomSheet(
        authViewModel = authViewModel,
        onTypeChange = { type ->
            showDialogType = type
        },
        showDialogType = showDialogType
    )
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

enum class AdminScreen {
    GALLERY,
    NEWS,
    STAFF
}