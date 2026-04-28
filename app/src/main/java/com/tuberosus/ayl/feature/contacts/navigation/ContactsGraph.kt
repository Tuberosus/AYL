package com.tuberosus.ayl.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tuberosus.ayl.feature.contacts.contacts_head.ContactsHeadScreenRoot
import com.tuberosus.ayl.feature.contacts.contacts_region.ContactsRegionScreenRoot
import com.tuberosus.ayl.feature.contacts.donation.DonationScreenRoot

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
                onDonationClick = {
                    navController.navigate(ContactsGraphRoutes.Donation)
                }
            )
        }
        composable<ContactsGraphRoutes.ContactsRegion> {
            ContactsRegionScreenRoot(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<ContactsGraphRoutes.Donation> {
            DonationScreenRoot(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}