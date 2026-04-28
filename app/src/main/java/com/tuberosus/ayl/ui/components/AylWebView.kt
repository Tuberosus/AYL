package com.tuberosus.ayl.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AylWebView(
    url: String,
) {
    val context = LocalContext.current

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            webViewClient = WebViewClient()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webView.destroy()
        }
    }

    AndroidView(
        factory = { webView },
        update = { view ->
            if (view.url != url) {
                view.loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}