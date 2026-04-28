package com.tuberosus.ayl.feature.contacts.contacts_head

sealed interface ContactsHeadEvents {
    data class OnSiteClick(val site: String) : ContactsHeadEvents
    data class OnEmailClick(val email: String) : ContactsHeadEvents
    data class OnPhoneClick(val phone: String) : ContactsHeadEvents
    data class OnTelegramClick(val link: String) : ContactsHeadEvents
    data class OnYoutubeClick(val link: String) : ContactsHeadEvents
    data class OnVkClick(val link: String) : ContactsHeadEvents
    data object OnRegionsContactsClick : ContactsHeadEvents
    data object OnDonationClick : ContactsHeadEvents
}