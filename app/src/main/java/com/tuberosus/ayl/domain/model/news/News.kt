package com.tuberosus.ayl.domain.model.news

data class News(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val date: Long,
    val linkUrl: String,
)
