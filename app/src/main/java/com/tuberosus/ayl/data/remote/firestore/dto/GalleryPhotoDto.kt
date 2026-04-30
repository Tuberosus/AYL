package com.tuberosus.ayl.data.remote.firestore.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class GalleryPhotoDto(
    val imageName: String = "",
    val title: String = ""
)
