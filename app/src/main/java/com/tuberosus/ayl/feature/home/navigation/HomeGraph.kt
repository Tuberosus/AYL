package com.tuberosus.ayl.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.home.about.AboutScreenRoot

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<HomeGraphRoutes.Graph>(
        startDestination = HomeGraphRoutes.About
    ) {
        composable<HomeGraphRoutes.About> {
            AboutScreenRoot()
        }
    }
}