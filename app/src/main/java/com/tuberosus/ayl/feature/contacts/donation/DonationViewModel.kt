package com.tuberosus.ayl.feature.contacts.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DonationViewModel : ViewModel() {
    private var _state = MutableStateFlow(DonationState())
    val state = _state.asStateFlow()
}