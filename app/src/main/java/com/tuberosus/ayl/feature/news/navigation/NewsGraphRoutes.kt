package com.tuberosus.ayl.feature.news.navigation

import com.tuberosus.ayl.navigation.Route
import kotlinx.serialization.Serializable

sealed interface NewsGraphRoutes : Route {
    @Serializable
    data object Graph : NewsGraphRoutes

    @Serializable
    data object NewsList : NewsGraphRoutes
}