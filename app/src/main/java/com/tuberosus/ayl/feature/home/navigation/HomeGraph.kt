package com.tuberosus.ayl.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.home.about.AboutScreenRoot
import com.tuberosus.ayl.feature.home.advantages.AdvantagesScreenRoot
import com.tuberosus.ayl.feature.home.documents.DocumentsScreenRoot

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation<HomeGraphRoutes.Graph>(
        startDestination = HomeGraphRoutes.About
    ) {
        composable<HomeGraphRoutes.About> {
            AboutScreenRoot(
                onAdvantagesClick = {
                    navController.navigate(HomeGraphRoutes.Advantages)
                },
                onDocumentsClick = {
                    navController.navigate(HomeGraphRoutes.Documents)
                }
            )
        }
        composable<HomeGraphRoutes.Advantages> {
            AdvantagesScreenRoot()
        }
        composable<HomeGraphRoutes.Documents> {
            DocumentsScreenRoot()
        }
    }
}