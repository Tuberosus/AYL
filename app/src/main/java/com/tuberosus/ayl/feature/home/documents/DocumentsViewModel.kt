package com.tuberosus.ayl.feature.home.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DocumentsViewModel : ViewModel() {
    private val eventChannel = Channel<DocumentsEvent>()
    val events = eventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(DocumentsState())
    val state = _state.asStateFlow()

    fun onAction(action: DocumentsAction) {
        when (action) {
            is DocumentsAction.OnBackClick ->
                sendEvent(DocumentsEvent.OnBackClick)
        }
    }

    private fun sendEvent(event: DocumentsEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}