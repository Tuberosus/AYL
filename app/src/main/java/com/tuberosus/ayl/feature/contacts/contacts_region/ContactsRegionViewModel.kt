package com.tuberosus.ayl.feature.contacts.contacts_region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ContactsRegionViewModel : ViewModel() {
    private var _state = MutableStateFlow(ContactsRegionState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ContactsRegionEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ContactsRegionAction) {
        when (action) {
            is ContactsRegionAction.OnBackClick ->
                sendEvent(ContactsRegionEvent.OnBackClick)

            is ContactsRegionAction.OnSocialMedialClick ->
                sendEvent(ContactsRegionEvent.OnSocialMedialClick(action.link))
        }
    }

    private fun sendEvent(event: ContactsRegionEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}