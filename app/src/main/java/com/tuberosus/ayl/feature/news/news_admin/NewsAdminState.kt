package com.tuberosus.ayl.feature.news.news_admin

data class NewsAdminState(
    val title: String = "",
    val photoUrl: String = "",
    val sourceUrl: String = "",
    val newsText: String = "",
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
)