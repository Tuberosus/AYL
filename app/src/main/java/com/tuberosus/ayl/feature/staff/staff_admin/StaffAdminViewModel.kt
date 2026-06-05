package com.tuberosus.ayl.feature.staff.staff_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.usecase.SaveStaffUseCase
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StaffAdminViewModel(
    private val saveStaffUseCase: SaveStaffUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(StaffAdminState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = StaffAdminState()
        )

    private val eventChannel = Channel<StaffAdminEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: StaffAdminAction) {
        when (action) {
            is StaffAdminAction.OnNameChange -> onNameChange(action.value)
            is StaffAdminAction.OnPositionChange -> onPositionChange(action.value)
            is StaffAdminAction.OnPhotoUrlChange -> onPhotoChange(action.value)
            is StaffAdminAction.OnTelegramLinkChange -> onTelegramLinkChange(action.value)
            is StaffAdminAction.OnBioChange -> onBioChange(action.value)
            StaffAdminAction.OnSave -> saveStaff()
            StaffAdminAction.OnDismiss -> clearState()
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                it.name.isNotBlank()
                        && it.position.isNotBlank()
                        && !it.isSaving
            }
            .distinctUntilChanged()
            .onEach { canSave ->
                _state.update { it.copy(canSave = canSave) }
            }
            .launchIn(viewModelScope)
    }

    fun saveStaff() {
        val state = _state.value

        if (!state.canSave) return

        viewModelScope.launch {
            _state.update {
                it.copy(isSaving = true)
            }
            val staff = Staff(
                name = state.name,
                position = state.position,
                photoName = state.photoUrl,
                telegramLink = state.telegramLink,
                bio = state.bio
            )

            saveStaffUseCase(staff)
                .onSuccess {
                    clearState()
                    eventChannel.send(StaffAdminEvent.SuccessSave)
                }
                .onFailure {
                    _state.update {
                        it.copy(isSaving = false)
                    }
                    eventChannel.send(
                        StaffAdminEvent.SaveErrorMessage(
                            "Ошибка при сохранении. Попробуйте позже."
                        )
                    )
                }
        }
    }

    private fun onNameChange(value: String) {
        _state.update {
            it.copy(name = value)
        }
    }

    private fun onPositionChange(value: String) {
        _state.update {
            it.copy(position = value)
        }
    }

    private fun onPhotoChange(value: String) {
        _state.update {
            it.copy(photoUrl = value)
        }
    }

    private fun onTelegramLinkChange(value: String) {
        _state.update {
            it.copy(telegramLink = value)
        }
    }

    private fun onBioChange(value: String) {
        _state.update {
            it.copy(bio = value)
        }
    }

    private fun clearState() {
        _state.update { StaffAdminState() }
    }
}