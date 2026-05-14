package com.tuberosus.ayl.data.remote.firestore.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class GalleryPhotoDto(
    override var id: String = "",
    val imageName: String = "",
    val title: String = ""
) : FirestoreDocument
