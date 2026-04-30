package com.tuberosus.ayl.feature.gallery.gallery_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.repository.GalleryRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryListViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {
    private var _state = MutableStateFlow(GalleryListState())
    val state = _state.asStateFlow()

    init {
        getGalleryPhotos()
    }

    private fun getGalleryPhotos() {
        viewModelScope.launch {
            galleryRepository.getGalleryPhotos()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            groupedPhotos = groupGalleriesByTitle(result)
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

    private fun groupGalleriesByTitle(galleries: List<GalleryPhoto>): Map<String, List<GalleryPhoto>> {
        return galleries.groupBy { it.title }
    }
}