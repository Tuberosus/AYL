package com.tuberosus.ayl.feature.contacts.contacts_head

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ContactsHeadViewModel : ViewModel() {

    private var _state = MutableStateFlow(ContactsHeadState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<ContactsHeadEvents>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ContactsHeadAction) {
        val contactsState = state.value.contacts
            when (action) {
                is ContactsHeadAction.OnSiteClick ->
                    sendEvent(ContactsHeadEvents.OnSiteClick(contactsState.site))

                is ContactsHeadAction.OnEmailClick ->
                    sendEvent(ContactsHeadEvents.OnEmailClick(contactsState.email))

                is ContactsHeadAction.OnPhoneClick ->
                    sendEvent(ContactsHeadEvents.OnPhoneClick(contactsState.phone))

                is ContactsHeadAction.OnTelegramClick ->
                    sendEvent(ContactsHeadEvents.OnTelegramClick(contactsState.telegram))

                is ContactsHeadAction.OnVkClick ->
                    sendEvent(ContactsHeadEvents.OnVkClick((contactsState.vk)))

                is ContactsHeadAction.OnYoutubeClick ->
                    sendEvent(ContactsHeadEvents.OnYoutubeClick(contactsState.youtube))

              else -> Unit
            }
    }

    private fun sendEvent(event: ContactsHeadEvents) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}