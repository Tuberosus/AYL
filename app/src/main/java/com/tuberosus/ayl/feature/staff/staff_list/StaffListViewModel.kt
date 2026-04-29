package com.tuberosus.ayl.feature.staff.staff_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StaffListViewModel : ViewModel() {
    private var _state = MutableStateFlow(StaffListState())
    val state = _state.asStateFlow()
}