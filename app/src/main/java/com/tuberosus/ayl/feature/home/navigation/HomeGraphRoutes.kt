package com.tuberosus.ayl.feature.home.navigation

import kotlinx.serialization.Serializable

sealed interface HomeGraphRoutes {
    @Serializable
    data object Graph : HomeGraphRoutes

    @Serializable
    data object About : HomeGraphRoutes

    @Serializable
    data object Advantages : HomeGraphRoutes

    @Serializable
    data object Documents
}