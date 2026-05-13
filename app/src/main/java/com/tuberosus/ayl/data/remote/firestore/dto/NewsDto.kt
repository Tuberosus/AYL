package com.tuberosus.ayl.data.remote.firestore.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class NewsDto(
    override var id: String = "",
    val title: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val date: Timestamp? = null,
    val linkUrl: String = "",
) : FirestoreDocument