package com.tuberosus.ayl.feature.gallery.gallery_list

sealed interface GalleryListAction {
    data class OnPhotoClick(val id: String) : GalleryListAction
    data object OnFullScreenGalleryCloseClick : GalleryListAction
}