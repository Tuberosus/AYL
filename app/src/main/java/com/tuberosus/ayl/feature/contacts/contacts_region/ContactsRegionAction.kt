package com.tuberosus.ayl.feature.contacts.contacts_region

sealed interface ContactsRegionAction {
    data object OnBackClick : ContactsRegionAction
    data class OnSocialMedialClick(val link: String) : ContactsRegionAction
}