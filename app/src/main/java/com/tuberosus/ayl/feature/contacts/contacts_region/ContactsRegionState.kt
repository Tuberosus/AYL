package com.tuberosus.ayl.feature.contacts.contacts_region

import com.tuberosus.ayl.config.RegionContacts
import com.tuberosus.ayl.domain.model.contacts.Region

data class ContactsRegionState(
    val regions: List<Region> = RegionContacts.contacts
)