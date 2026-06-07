package com.tuberosus.ayl.feature.staff.staff_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.usecase.SaveStaffUseCase
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import com.tuberosus.ayl.feature.staff.staff_admin.model.StaffDraft
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
            is StaffAdminAction.SetStaffForUpdate -> setStaffForUpdate(action.staff)
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                it.staffDraft.name.isNotBlank()
                        && it.staffDraft.position.isNotBlank()
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
                id = state.staffDraft.id,
                name = state.staffDraft.name,
                position = state.staffDraft.position,
                photoName = state.staffDraft.photoUrl,
                telegramLink = state.staffDraft.telegramLink,
                bio = state.staffDraft.bio
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
        _state.update { currentState ->
            currentState.copy(
                staffDraft = currentState.staffDraft.copy(
                    name = value
                )
            )
        }
    }

    private fun onPositionChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                staffDraft = currentState.staffDraft.copy(
                    position = value
                )
            )
        }
    }

    private fun onPhotoChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                staffDraft = currentState.staffDraft.copy(
                    photoUrl = value
                )
            )
        }
    }

    private fun onTelegramLinkChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                staffDraft = currentState.staffDraft.copy(
                    telegramLink = value
                )
            )
        }
    }

    private fun onBioChange(value: String) {
        _state.update { currentState ->
            currentState.copy(
                staffDraft = currentState.staffDraft.copy(
                    bio = value
                )
            )
        }
    }

    private fun clearState() {
        _state.update { StaffAdminState() }
    }

    private fun setStaffForUpdate(staff: Staff?) {
        _state.update { currentState ->
            currentState.copy(
                staffDraft = StaffDraft(
                    id = staff?.id ?: "",
                    name = staff?.name ?: "",
                    position = staff?.position ?: "",
                    photoUrl = staff?.photoName ?: "",
                    telegramLink = staff?.telegramLink ?: "",
                    bio = staff?.bio ?: "",
                )
            )
        }
    }
}