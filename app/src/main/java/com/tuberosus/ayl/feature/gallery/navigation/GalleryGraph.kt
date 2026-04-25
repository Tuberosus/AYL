package com.tuberosus.ayl.feature.gallery.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.gallery.gallery_list.GalleryListScreenRoot

fun NavGraphBuilder.galleryGraph() {
    navigation<GalleryGraphRoutes.Graph>(
        startDestination = GalleryGraphRoutes.GalleryList
    ) {
        composable<GalleryGraphRoutes.GalleryList> {
            GalleryListScreenRoot()
        }
    }
}