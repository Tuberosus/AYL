package com.tuberosus.ayl.feature.news.navigation

import kotlinx.serialization.Serializable

sealed interface NewsGraphRoutes {
    @Serializable
    data object Graph : NewsGraphRoutes

    @Serializable
    data object NewsList : NewsGraphRoutes
}