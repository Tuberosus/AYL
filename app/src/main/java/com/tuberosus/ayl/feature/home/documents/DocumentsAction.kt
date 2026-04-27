package com.tuberosus.ayl.feature.home.documents

import android.webkit.WebView

sealed interface DocumentsAction {
    data object OnBackClick : DocumentsAction
    data class SetWebView(val webView: WebView) : DocumentsAction
    data class LoadingChange(val isLoading: Boolean) : DocumentsAction
}
