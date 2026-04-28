package com.tuberosus.ayl.config

import com.tuberosus.ayl.config.RegionContact

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
        RegionContact(
            telegram = "https://t.me/aylaltay"
        ),
        RegionContact(
            telegram = "https://t.me/ayl_krd",
            site = "https://aylkrd.tilda.ws",
            vk = "https://m.vk.com/ayl_krd"
        ),
        RegionContact(
            vk = "https://m.vk.com/club241886"
        ),
        RegionContact(
            telegram = "https://t.me/AYLOrel"
        ),
        RegionContact(
            telegram = "https://t.me/tomskaul"
        )
    )
}

data class RegionContact(
    val telegram: String? = null,
    val site: String? = null,
    val vk: String? = null,
)