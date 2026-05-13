package com.tuberosus.ayl.feature.news.news_list

sealed interface NewsListAction {
    data class OnNewsClick(val newsId: String) : NewsListAction
}