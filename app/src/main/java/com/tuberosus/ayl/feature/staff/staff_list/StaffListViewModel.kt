package com.tuberosus.ayl.feature.staff.staff_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.StaffRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StaffListViewModel(
    private val staffRepository: StaffRepository
) : ViewModel() {
    private val eventChannel = Channel<StaffListEvent>()
    val events = eventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(StaffListState())
    val state = _state.asStateFlow()

    init {
        getStaff()
    }

    fun onAction(action: StaffListAction) {
        when (action) {
            is StaffListAction.OnTgClick ->
                sendEvent(StaffListEvent.OnTgClick(action.telegram))

            is StaffListAction.OnRetryClick ->
                getStaff()
        }
    }

    private fun getStaff() {
        viewModelScope.launch {
            staffRepository.getStaff()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            staff = result,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            staff = null,
                            error = error
                        )
                    }
                }
        }
    }

    private fun sendEvent(event: StaffListEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }
}