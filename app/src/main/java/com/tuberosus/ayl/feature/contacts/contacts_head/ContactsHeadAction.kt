package com.tuberosus.ayl.feature.contacts.contacts_head

sealed interface ContactsHeadAction {
    data object OnSiteClick : ContactsHeadAction
    data object OnEmailClick : ContactsHeadAction
    data object OnPhoneClick : ContactsHeadAction
    data object OnTelegramClick : ContactsHeadAction
    data object OnYoutubeClick : ContactsHeadAction
    data object OnVkClick : ContactsHeadAction
    data object OnRegionsContactsClick : ContactsHeadAction
    data object OnDonationClick : ContactsHeadAction
}