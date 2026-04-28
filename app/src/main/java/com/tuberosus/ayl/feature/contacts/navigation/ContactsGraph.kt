package com.tuberosus.ayl.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.contacts.contacts_head.ContactsHeadScreenRoot
import com.tuberosus.ayl.feature.contacts.contacts_region.ContactsRegionScreenRoot

fun NavGraphBuilder.contactsGraph(
    navController: NavController
) {
    navigation<ContactsGraphRoutes.Graph>(
        startDestination = ContactsGraphRoutes.ContactsHead
    ) {
        composable<ContactsGraphRoutes.ContactsHead> {
            ContactsHeadScreenRoot(
                onRegionsContactsClick = {
                    navController.navigate(ContactsGraphRoutes.ContactsRegion)
                },
                onDonationClick = {}
            )
        }
        composable<ContactsGraphRoutes.ContactsRegion> {
            ContactsRegionScreenRoot(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}