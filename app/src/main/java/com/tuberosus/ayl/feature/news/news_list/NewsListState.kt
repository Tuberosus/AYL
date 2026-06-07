package com.tuberosus.ayl.feature.news.news_list

import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.util.AppError

data class NewsListState(
    val isLoading: Boolean = true,
    val news: List<News>? = null,
    val error: AppError? = null,
    val isEditMenuOpen: Boolean = false,
    val selectedNews: News? = null,
)