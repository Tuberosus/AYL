package com.tuberosus.ayl.feature.home.documents

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.ui.components.BackTopAppBar
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Blue
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentsScreenRoot(
    onBackClick: () -> Unit,
    viewModel: DocumentsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is DocumentsEvent.OnBackClick -> onBackClick()
        }
    }

    DocumentsScreen(
        state = state,
        onBackClick = {
            viewModel.onAction(DocumentsAction.OnBackClick)
        },
        setWebView = {
            viewModel.onAction(DocumentsAction.SetWebView(it))
        },
        onLoadingChange = {
            viewModel.onAction(DocumentsAction.LoadingChange(it))
        },
    )
}

@Composable
private fun DocumentsScreen(
    state: DocumentsState,
    onBackClick: () -> Unit,
    setWebView: (WebView) -> Unit,
    onLoadingChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackTopAppBar(
            onBackClick = onBackClick,
        )
        DocumentsWebView(
            url = state.url,
            setWebView = setWebView,
            onLoadingChange = onLoadingChange,
        )

    }
}

@Composable
private fun DocumentsWebView(
    url: String,
    setWebView: (WebView) -> Unit,
    onLoadingChange: (Boolean) -> Unit,
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        onLoadingChange(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        onLoadingChange(false)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        return false
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    }
                }

                loadUrl(url)
                setWebView(this)
            }
        },
        update = { view ->
            if (view.url != url) {
                view.loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
private fun DocumentsScreenPreview() {
    AYLTheme {
        DocumentsScreen(
            state = DocumentsState(),
            onBackClick = {},
            setWebView = {},
            onLoadingChange = {},
        )
    }
}