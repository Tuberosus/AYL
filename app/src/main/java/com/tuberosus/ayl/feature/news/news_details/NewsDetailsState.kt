package com.tuberosus.ayl.feature.news.news_details

import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.util.AppError

data class NewsDetailsState(
    val isLoading: Boolean = true,
    val news: News? = null,
    val error: AppError? = null,
)