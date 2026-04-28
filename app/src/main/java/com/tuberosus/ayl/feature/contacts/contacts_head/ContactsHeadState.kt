package com.tuberosus.ayl.feature.contacts.contacts_head

import com.tuberosus.ayl.config.CompanyContacts
import com.tuberosus.ayl.domain.model.HeadContacts

data class ContactsHeadState(
    val contacts: HeadContacts = HeadContacts()
)