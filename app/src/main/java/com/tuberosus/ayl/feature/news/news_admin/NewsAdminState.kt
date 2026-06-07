package com.tuberosus.ayl.feature.news.news_admin

import com.tuberosus.ayl.feature.news.news_admin.model.NewsDraft

data class NewsAdminState(
    val newsDraft: NewsDraft = NewsDraft(),
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
)