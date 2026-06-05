package com.tuberosus.ayl.feature.news.news_admin

sealed interface NewsAdminEvent {
    data object SuccessSave : NewsAdminEvent
    data class SaveErrorMessage(val message: String) : NewsAdminEvent
}