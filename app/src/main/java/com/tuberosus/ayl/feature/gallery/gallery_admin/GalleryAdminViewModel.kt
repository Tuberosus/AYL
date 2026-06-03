package com.tuberosus.ayl.feature.gallery.gallery_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.usecase.SaveToGalleryUseCase
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

class GalleryAdminViewModel(
    private val saveToGalleryUseCase: SaveToGalleryUseCase
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(GalleryAdminState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            GalleryAdminState()
        )

    private val eventChannel = Channel<GalleryAdminEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GalleryAdminAction) {
        when (action) {
            is GalleryAdminAction.OnPhotoLinkChange ->
                changePhotoLinks(action.value)

            is GalleryAdminAction.OnTitleChange ->
                changeTitle(action.value)

            GalleryAdminAction.OnSave ->
                savePhotosToGallery()

            GalleryAdminAction.OnDismiss ->
                clearState()
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                it.eventTitle.isNotBlank() &&
                        it.photoLinks.isNotEmpty() &&
                        !it.isSaving
            }
            .distinctUntilChanged()
            .onEach { canSave ->
                _state.update { it.copy(canSave = canSave) }
            }
            .launchIn(viewModelScope)
    }

    private fun changePhotoLinks(value: String) {
        val links = value.split("\n")
        _state.update { it.copy(photoLinks = links) }
    }

    private fun changeTitle(value: String) {
        _state.update { it.copy(eventTitle = value) }
    }


    private fun savePhotosToGallery() {
        viewModelScope.launch {
            if (state.value.canSave) {
                _state.update {
                    it.copy(isSaving = true)
                }

                val galleryPhotos = state.value.photoLinks.map { link ->
                    GalleryPhoto(
                        id = "",
                        title = state.value.eventTitle,
                        imageName = link
                    )
                }
                saveToGalleryUseCase(galleryPhotos)
                    .onSuccess {
                        _state.update { GalleryAdminState() }
                        eventChannel.send(GalleryAdminEvent.SuccessSave)
                    }
                    .onFailure {
                        _state.update {
                            it.copy(isSaving = false)
                        }
                        eventChannel.send(
                            GalleryAdminEvent.SaveErrorMessage(
                                "Ошибка при загрузке фотографий. Попробуйте позже."
                            )
                        )
                    }
            }
        }
    }

    private fun clearState() {
        _state.update { GalleryAdminState() }
    }
}