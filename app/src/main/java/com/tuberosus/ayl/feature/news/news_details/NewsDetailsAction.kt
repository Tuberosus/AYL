package com.tuberosus.ayl.feature.news.news_details

sealed interface NewsDetailsAction {
    data object OnBackClick : NewsDetailsAction
    data class OnEventLinkClick(val link: String) : NewsDetailsAction
}