package com.tuberosus.ayl.feature.gallery.navigation

import com.tuberosus.ayl.navigation.Route
import kotlinx.serialization.Serializable

sealed interface GalleryGraphRoutes : Route {
    @Serializable
    data object Graph : GalleryGraphRoutes

    @Serializable
    data object GalleryList
}