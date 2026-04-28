package com.tuberosus.ayl.feature.contacts.donation

sealed interface DonationEvent {
    data object OnBackClick : DonationEvent
}