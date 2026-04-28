package com.tuberosus.ayl.feature.contacts.contacts_region

sealed interface ContactsRegionEvent {
    data object OnBackClick : ContactsRegionEvent
    data class OnSocialMedialClick(val link: String) : ContactsRegionEvent
}