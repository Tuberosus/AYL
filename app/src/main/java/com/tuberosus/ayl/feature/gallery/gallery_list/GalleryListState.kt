package com.tuberosus.ayl.feature.gallery.gallery_list

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.util.AppError

data class GalleryListState(
    val isLoading: Boolean = true,
    val groupedPhotos: Map<String, List<GalleryPhoto>>? = null,
    val photos: List<String>? = null,
    val error: AppError? = null,
    val isFullScreenPhotoOpen: Boolean = false,
    val startIndex: Int = 0,
    val isEditMenuOpen: Boolean = false,
    val selectedPhoto: GalleryPhoto? = null,
)