package com.tuberosus.ayl.config

import com.tuberosus.ayl.domain.model.contacts.Region
import com.tuberosus.ayl.domain.model.contacts.RegionContact

object CompanyContacts {
    const val SITE = "https://ayl.ru/"
    const val EMAIL = "info@ayl.ru"
    const val PHONE = "+7 (910) 260-58-29"
    const val TELEGRAM = "https://t.me/aylrus"
    const val VK = "https://vk.com/aylrussia"
    const val YOUTUBE = "https://m.youtube.com/@AYL_Russia?ra=m"
}

object RegionContacts {
    val contacts = listOf(
        Region(
            name = "Республика Алтай",
            contacts = RegionContact(
                telegram = "https://t.me/aylaltay"
            )
        ),
        Region(
            name = "Краснодарский край",
            contacts = RegionContact(
                telegram = "https://t.me/ayl_krd",
                site = "https://aylkrd.tilda.ws",
                vk = "https://m.vk.com/ayl_krd"
            )
        ),
        Region(
            name = "Барнаул",
            contacts = RegionContact(
                vk = "https://m.vk.com/club241886"
            )
        ),
        Region(
            name = "Орёл",
            contacts = RegionContact(
                telegram = "https://t.me/AYLOrel"
            )
        ),
        Region(
            name = "Томск",
            contacts = RegionContact(
                telegram = "https://t.me/tomskaul"
            )
        )
    )
}