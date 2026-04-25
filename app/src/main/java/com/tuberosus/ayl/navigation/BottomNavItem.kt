package com.tuberosus.ayl.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tuberosus.ayl.R
import com.tuberosus.ayl.feature.contacts.navigation.ContactsGraphRoutes
import com.tuberosus.ayl.feature.gallery.navigation.GalleryGraphRoutes
import com.tuberosus.ayl.feature.home.navigation.HomeGraphRoutes
import com.tuberosus.ayl.feature.news.navigation.NewsGraphRoutes
import com.tuberosus.ayl.feature.staff.navigation.StaffGraphRoutes

sealed class BottomNavItem(
    val route: Route,
    @field:DrawableRes val icon: Int,
    @field:StringRes val title: Int,
) {
    data object Home : BottomNavItem(
        route = HomeGraphRoutes.Graph,
        icon = R.drawable.ic_home,
        title = R.string.nav_title_home,
    )
    data object Gallery : BottomNavItem(
        route = GalleryGraphRoutes.Graph,
        icon = R.drawable.ic_photo_library,
        title = R.string.nav_title_gallery,
    )
    data object News : BottomNavItem(
        route = NewsGraphRoutes.Graph,
        icon = R.drawable.ic_news,
        title = R.string.nav_title_news,
    )
    data object Staff : BottomNavItem(
        route = StaffGraphRoutes.Graph,
        icon = R.drawable.ic_groups,
        title = R.string.nav_title_staff,
    )
    data object Contacts : BottomNavItem(
        route = ContactsGraphRoutes.Graph,
        icon = R.drawable.ic_distance,
        title = R.string.nav_title_contacts,
    )
}