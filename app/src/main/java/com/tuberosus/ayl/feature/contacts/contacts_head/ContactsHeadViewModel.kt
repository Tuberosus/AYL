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
        sendEvent(
            when (action) {
                is ContactsHeadAction.OnSiteClick ->
                    ContactsHeadEvents.OnSiteClick(contactsState.site)

                is ContactsHeadAction.OnEmailClick ->
                    ContactsHeadEvents.OnEmailClick(contactsState.email)

                is ContactsHeadAction.OnPhoneClick ->
                    ContactsHeadEvents.OnPhoneClick(contactsState.phone)

                is ContactsHeadAction.OnTelegramClick ->
                    ContactsHeadEvents.OnTelegramClick(contactsState.telegram)

                is ContactsHeadAction.OnVkClick ->
                    ContactsHeadEvents.OnVkClick((contactsState.vk))

                is ContactsHeadAction.OnYoutubeClick ->
                    ContactsHeadEvents.OnYoutubeClick(contactsState.youtube)

                is ContactsHeadAction.OnRegionsContactsClick ->
                    ContactsHeadEvents.OnRegionsContactsClick

                is ContactsHeadAction.OnDonationClick ->
                    ContactsHeadEvents.OnDonationClick
            }
        )
    }

    private fun sendEvent(event: ContactsHeadEvents) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}