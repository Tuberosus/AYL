package com.tuberosus.ayl.feature.home.advantages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AdvantagesViewModel : ViewModel() {
    private val eventChannel = Channel<AdvantagesEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: AdvantagesAction) {
        when (action) {
            is AdvantagesAction.OnBackClick -> sendOnBackEvent()
        }
    }

    private fun sendOnBackEvent() {
        viewModelScope.launch {
            eventChannel.send(AdvantagesEvent.OnBackClick)
        }
    }
}