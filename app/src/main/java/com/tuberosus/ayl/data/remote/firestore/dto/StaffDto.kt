package com.tuberosus.ayl.data.remote.firestore.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class StaffDto(
    val name: String = "",
    val position: String = "",
    val bio: String = "",
    val photoName: String = "",
    val telegramLink: String = "",
)