package com.tuberosus.ayl.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tuberosus.ayl.feature.contacts.navigation.ContactsGraphRoutes
import com.tuberosus.ayl.feature.gallery.navigation.GalleryGraphRoutes
import com.tuberosus.ayl.feature.home.navigation.HomeGraphRoutes
import com.tuberosus.ayl.feature.news.navigation.NewsGraphRoutes
import com.tuberosus.ayl.feature.staff.navigation.StaffGraphRoutes
import com.tuberosus.ayl.ui.theme.Blue

@Composable
fun AppBottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Gallery,
        BottomNavItem.News,
        BottomNavItem.Staff,
        BottomNavItem.Contacts
    )

    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(20)),
        tonalElevation = 16.dp,
    ) {
        val backStack by navController.currentBackStackEntryAsState()

        items.forEach { item ->
            val selected = backStack
                ?.destination
                ?.hierarchy
                ?.any {
                    when (item) {
                        BottomNavItem.Home ->
                            it.hasRoute<HomeGraphRoutes.Graph>()

                        BottomNavItem.Gallery ->
                            it.hasRoute<GalleryGraphRoutes.Graph>()

                        BottomNavItem.News ->
                            it.hasRoute<NewsGraphRoutes.Graph>()

                        BottomNavItem.Staff ->
                            it.hasRoute<StaffGraphRoutes.Graph>()

                        BottomNavItem.Contacts ->
                            it.hasRoute<ContactsGraphRoutes.Graph>()
                    }
                } == true

            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    selectedTextColor = Blue,
                    indicatorColor = Color.Transparent
                ),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title)
                    )
                       },
                label = {
                    Text(
                        text = stringResource(item.title)
                    )
                }
            )
        }
    }
}