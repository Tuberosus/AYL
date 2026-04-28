package com.tuberosus.ayl.feature.contacts.navigation

import com.tuberosus.ayl.navigation.Route
import kotlinx.serialization.Serializable

sealed interface ContactsGraphRoutes : Route {
    @Serializable
    data object Graph : ContactsGraphRoutes

    @Serializable
    data object ContactsHead : ContactsGraphRoutes

    @Serializable
    data object ContactsRegion : ContactsGraphRoutes

    @Serializable
    data object Donation : ContactsGraphRoutes
}