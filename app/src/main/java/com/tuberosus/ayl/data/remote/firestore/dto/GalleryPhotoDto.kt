package com.tuberosus.ayl.data.remote.firestore.dto

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class GalleryPhotoDto(
    @get:Exclude
    override var id: String = "",
    val imageName: String = "",
    val title: String = ""
) : FirestoreDocument
