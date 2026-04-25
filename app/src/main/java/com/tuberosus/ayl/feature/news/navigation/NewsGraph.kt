package com.tuberosus.ayl.feature.news.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.news.news_list.NewsListScreenRoot

fun NavGraphBuilder.newsGraph() {
    navigation<NewsGraphRoutes.Graph>(
        startDestination = NewsGraphRoutes.NewsList
    ) {
        composable<NewsGraphRoutes.NewsList> {
            NewsListScreenRoot()
        }
    }
}