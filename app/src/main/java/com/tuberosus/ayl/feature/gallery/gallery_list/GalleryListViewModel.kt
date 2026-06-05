package com.tuberosus.ayl.feature.gallery.gallery_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.repository.GalleryRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import com.tuberosus.ayl.ui.util.isNonNegative
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryListViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {
    private var _state = MutableStateFlow(GalleryListState())
    val state = _state.asStateFlow()

    init {
        observeGalleryPhotos()
    }

    fun onAction(action: GalleryListAction) {
        when (action) {
            is GalleryListAction.OnPhotoClick ->
                openFullPhotoGallery(action.id)

            is GalleryListAction.OnFullScreenGalleryCloseClick ->
                closeOpenFullPhotoGallery()
        }
    }

    private fun getGalleryPhotos() {
        viewModelScope.launch {
            galleryRepository.getGalleryPhotos()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            groupedPhotos = groupGalleriesByTitle(result),
                            error = null,
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            groupedPhotos = null,
                            error = error
                        )
                    }
                }
        }
    }

    private fun observeGalleryPhotos() {
        galleryRepository.observeGalleryPhotos()
            .onEach { result ->
                result
                    .onSuccess { galleries ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                groupedPhotos = groupGalleriesByTitle(galleries),
                                error = null
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                groupedPhotos = null,
                                error = error
                            )
                        }
                    }
            }
            .launchIn(viewModelScope)
    }

    private fun groupGalleriesByTitle(galleries: List<GalleryPhoto>): Map<String, List<GalleryPhoto>> {
        return galleries.groupBy { it.title }
            .toSortedMap()
    }

    private fun openFullPhotoGallery(id: String) {
        val photos = state.value.groupedPhotos?.values?.flatten()
        val targetIndex = photos?.indexOfFirst { it.id == id }

        if (targetIndex.isNonNegative) {
            _state.update {
                it.copy(
                    photos = photos?.map { photo -> photo.imageName },
                    isFullScreenPhotoOpen = true,
                    startIndex = targetIndex!!
                )
            }
        }
    }

    private fun closeOpenFullPhotoGallery() {
        _state.update {
            it.copy(
                isFullScreenPhotoOpen = false,
                startIndex = -1
            )
        }
    }
}