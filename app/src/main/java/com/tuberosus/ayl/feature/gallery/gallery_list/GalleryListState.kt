package com.tuberosus.ayl.feature.gallery.gallery_list

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.util.AppError

data class GalleryListState(
    val isLoading: Boolean = true,
    val groupedPhotos: Map<String, List<GalleryPhoto>>? = null,
    val error: AppError? = null,
)