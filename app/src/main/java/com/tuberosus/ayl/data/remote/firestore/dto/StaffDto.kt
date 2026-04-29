package com.tuberosus.ayl.data.remote.firestore.dto

data class StaffDto(
    val id: String,
    var name: String,
    var position: String,
    var bio: String,
    var photoName: String,
    var telegramLink: String,
)