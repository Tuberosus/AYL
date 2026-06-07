package com.tuberosus.ayl.feature.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.feature.news.news_details.NewsDetailsScreenRoot
import com.tuberosus.ayl.feature.news.news_list.NewsListScreenRoot

fun NavGraphBuilder.newsGraph(
    onEditNews: (News?) -> Unit,
    navController: NavController
) {
    navigation<NewsGraphRoutes.Graph>(
        startDestination = NewsGraphRoutes.NewsList
    ) {
        composable<NewsGraphRoutes.NewsList> {
            NewsListScreenRoot(
                onNewsClick = { newsId ->
                    navController.navigate(NewsGraphRoutes.NewsDetails(newsId))
                },
                onNewsEdit = onEditNews
            )
        }
        composable<NewsGraphRoutes.NewsDetails> {
            NewsDetailsScreenRoot(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}