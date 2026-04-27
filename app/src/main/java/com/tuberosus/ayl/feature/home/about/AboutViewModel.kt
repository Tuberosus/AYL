package com.tuberosus.ayl.feature.home.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AboutViewModel : ViewModel() {
    private val eventChannel = Channel<AboutEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: AboutAction) {
        when (action) {
            is AboutAction.OnAdvantagesClick -> sendOnAdvantagesClickEvent()
            is AboutAction.OnDocumentsClick -> sendOnDocumentsClickEvent()
        }
    }

    private fun sendOnAdvantagesClickEvent() {
        viewModelScope.launch {
            eventChannel.send(AboutEvent.OnAdvantagesClick)
        }
    }

    private fun sendOnDocumentsClickEvent() {
        viewModelScope.launch {
            eventChannel.send(AboutEvent.OnDocumentsClick)
        }
    }
}