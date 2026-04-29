package com.tuberosus.ayl.domain.model.staff

data class Staff(
    val id: String,
    var name: String,
    var position: String,
    var bio: String,
    var photoName: String,
    var telegramLink: String,
)