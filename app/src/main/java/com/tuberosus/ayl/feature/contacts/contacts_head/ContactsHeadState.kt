package com.tuberosus.ayl.feature.contacts.contacts_head

import com.tuberosus.ayl.domain.model.contacts.HeadContacts

data class ContactsHeadState(
    val contacts: HeadContacts = HeadContacts()
)