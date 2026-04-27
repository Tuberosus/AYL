package com.tuberosus.ayl.feature.home.documents

import android.webkit.WebView
import com.tuberosus.ayl.BuildConfig

data class DocumentsState(
    val url: String = BuildConfig.DOCUMENTS_URL,
    val webView: WebView? = null,
    val isLoading: Boolean = true,
    val progress: Int = 0,
)
