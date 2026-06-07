package com.tuberosus.ayl.feature.gallery.gallery_list

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto

sealed interface GalleryListAction {
    data class OnPhotoClick(val id: String) : GalleryListAction
    data class OnPhotoLongClick(val photo: GalleryPhoto) : GalleryListAction
    data object OnFullScreenGalleryCloseClick : GalleryListAction
    data object OnDeleteClick : GalleryListAction
    data object OnDismissClick : GalleryListAction
}