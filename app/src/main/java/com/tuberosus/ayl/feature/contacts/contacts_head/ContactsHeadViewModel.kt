package com.tuberosus.ayl.feature.contacts.contacts_head

import androidx.lifecycle.ViewModel
import com.tuberosus.ayl.feature.home.about.AboutAction
import com.tuberosus.ayl.feature.home.about.AboutEvent
import com.tuberosus.ayl.feature.home.documents.DocumentsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ContactsHeadViewModel : ViewModel() {

    private var _state = MutableStateFlow(ContactsHeadState())
    val state = _state.asStateFlow()

//    private val eventChannel = Channel<AboutEvent>()
//    val events = eventChannel.receiveAsFlow()
//
//    fun onAction(action: AboutAction) {
//        when (action) {
//            is AboutAction.OnAdvantagesClick -> sendOnAdvantagesClickEvent()
//            is AboutAction.OnDocumentsClick -> sendOnDocumentsClickEvent()
//        }
//    }
}