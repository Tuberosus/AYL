package com.tuberosus.ayl.feature.home.navigation

import com.tuberosus.ayl.navigation.Route
import kotlinx.serialization.Serializable

sealed interface HomeGraphRoutes : Route {
    @Serializable
    data object Graph : HomeGraphRoutes

    @Serializable
    data object About : HomeGraphRoutes

    @Serializable
    data object Advantages : HomeGraphRoutes

    @Serializable
    data object Documents
}