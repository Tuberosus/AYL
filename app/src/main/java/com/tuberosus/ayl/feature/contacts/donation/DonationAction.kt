package com.tuberosus.ayl.feature.contacts.donation

sealed interface DonationAction {
    data object OnBackClick : DonationAction
}