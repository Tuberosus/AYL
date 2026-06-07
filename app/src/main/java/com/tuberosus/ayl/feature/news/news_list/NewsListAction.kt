package com.tuberosus.ayl.feature.news.news_list

import com.tuberosus.ayl.domain.model.news.News

sealed interface NewsListAction {
    data class OnNewsClick(val newsId: String) : NewsListAction
    data class OnNewsLongClick(val news: News) : NewsListAction
    data object OnDeleteClick : NewsListAction
    data object OnDismissClick : NewsListAction
}