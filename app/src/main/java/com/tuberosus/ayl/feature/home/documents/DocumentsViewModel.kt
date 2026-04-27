package com.tuberosus.ayl.feature.home.documents

import android.webkit.WebView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DocumentsViewModel : ViewModel() {
    private val eventChannel = Channel<DocumentsEvent>()
    val events = eventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(DocumentsState())
    val state = _state.asStateFlow()

    fun onAction(action: DocumentsAction) {
        when (action) {
            is DocumentsAction.OnBackClick ->
                sendOnBackEvent()

            is DocumentsAction.SetWebView ->
                setWebView(action.webView)

            is DocumentsAction.LoadingChange ->
                changeLoadingValue(action.isLoading)
        }
    }

    private fun sendOnBackEvent() {
        viewModelScope.launch {
            eventChannel.send(DocumentsEvent.OnBackClick)
        }
    }

    private fun setWebView(webView: WebView) {
        _state.update {
            it.copy(webView = webView)
        }
    }

    private fun changeLoadingValue(isLoading: Boolean) {
        _state.update {
            it.copy(isLoading = isLoading)
        }
    }
}