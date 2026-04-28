package com.tuberosus.ayl.feature.contacts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.contacts.contacts_head.ContactsHeadScreenRoot

fun NavGraphBuilder.contactsGraph() {
    navigation<ContactsGraphRoutes.Graph>(
        startDestination = ContactsGraphRoutes.ContactsHead
    ) {
        composable<ContactsGraphRoutes.ContactsHead> {
            ContactsHeadScreenRoot(
                onRegionsContactsClick = {},
                onDonationClick = {}
            )
        }
    }
}