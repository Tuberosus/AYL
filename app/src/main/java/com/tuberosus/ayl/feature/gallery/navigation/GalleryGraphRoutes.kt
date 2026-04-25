package com.tuberosus.ayl.feature.gallery.navigation

import kotlinx.serialization.Serializable

sealed interface GalleryGraphRoutes {
    @Serializable
    data object Graph : GalleryGraphRoutes

    @Serializable
    data object GalleryList
}