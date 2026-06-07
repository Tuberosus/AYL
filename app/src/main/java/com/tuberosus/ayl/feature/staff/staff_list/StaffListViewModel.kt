package com.tuberosus.ayl.feature.staff.staff_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.repository.StaffRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import com.tuberosus.ayl.feature.staff.staff_list.StaffListEvent.InfoMessage
import com.tuberosus.ayl.feature.staff.staff_list.StaffListEvent.OnTgClick
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        observeStaff()
    }

    fun onAction(action: StaffListAction) {
        when (action) {
            is StaffListAction.OnTgClick ->
                sendEvent(OnTgClick(action.telegram))

            StaffListAction.OnDeleteClick -> deleteStaff()
            StaffListAction.OnDismissClick -> closeEditMenu()
            is StaffListAction.OnStaffLongClick -> openEditMenu(action.staff)
        }
    }

    private fun observeStaff() {
        staffRepository.observeStaff()
            .onEach { result ->
                result
                    .onSuccess { staff ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                staff = staff,
                                error = null
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = error
                            )
                        }
                    }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteStaff() {
        viewModelScope.launch {
            _state.value.selectedStaff?.let { staff ->
                staffRepository.deleteStaff(staff.id)
                    .onSuccess {
                        sendEvent(
                            InfoMessage("Контакт удален")
                        )
                    }
                    .onFailure {
                        sendEvent(
                            InfoMessage("Не удалось удалить контакт")
                        )
                    }
            }
            closeEditMenu()
        }
    }

    private fun sendEvent(event: StaffListEvent) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    private fun openEditMenu(staff: Staff) {
        _state.update {
            it.copy(
                selectedStaff = staff,
                isEditMenuOpen = true,
            )
        }
    }

    private fun closeEditMenu() {
        _state.update {
            it.copy(
                isEditMenuOpen = false,
                selectedStaff = null
            )
        }
    }
}