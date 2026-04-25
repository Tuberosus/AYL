package com.tuberosus.ayl.feature.contacts.navigation

import kotlinx.serialization.Serializable

sealed interface ContactsGraphRoutes {
    @Serializable
    data object Graph : ContactsGraphRoutes

    @Serializable
    data object ContactsHead : ContactsGraphRoutes
}