package com.tuberosus.ayl.feature.news.news_details

sealed interface NewsDetailsEvent {
    data class OnEventLinkClick(val link: String) : NewsDetailsEvent
}