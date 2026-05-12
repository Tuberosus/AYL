package com.tuberosus.ayl.feature.home.documents

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DocumentsViewModel : ViewModel() {
    private var _state = MutableStateFlow(DocumentsState())
    val state = _state.asStateFlow()
}