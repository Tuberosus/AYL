package com.tuberosus.ayl.feature.news.news_list

sealed interface NewsListEvent {
    data class InfoMessage(val message: String) : NewsListEvent
}