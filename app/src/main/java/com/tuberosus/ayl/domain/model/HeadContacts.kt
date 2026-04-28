package com.tuberosus.ayl.domain.model

import com.tuberosus.ayl.config.CompanyContacts

data class HeadContacts(
    val site: String = CompanyContacts.SITE,
    val email: String = CompanyContacts.EMAIL,
    val phone: String = CompanyContacts.PHONE,
    val telegram: String = CompanyContacts.TELEGRAM,
    val vk: String = CompanyContacts.VK,
    val youtube: String = CompanyContacts.YOUTUBE,
)
