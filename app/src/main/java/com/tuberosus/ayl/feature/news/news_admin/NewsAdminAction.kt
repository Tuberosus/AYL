package com.tuberosus.ayl.feature.news.news_admin

import com.tuberosus.ayl.domain.model.news.News

sealed interface NewsAdminAction {
    data class OnTitleChange(val value: String) : NewsAdminAction
    data class OnPhotoUrlChange(val value: String) : NewsAdminAction
    data class OnSourceUrlChange(val value: String) : NewsAdminAction
    data class OnNewsTextChange(val value: String) : NewsAdminAction
    data object OnSave : NewsAdminAction
    data object OnDismiss : NewsAdminAction
    data class SetNewsForUpdate(val newsForUpdate: News?) : NewsAdminAction
}