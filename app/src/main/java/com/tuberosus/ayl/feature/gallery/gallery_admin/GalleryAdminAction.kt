package com.tuberosus.ayl.feature.gallery.gallery_admin

sealed interface GalleryAdminAction {
    data class OnTitleChange(val value: String) : GalleryAdminAction
    data class OnPhotoLinkChange(val value: String) : GalleryAdminAction
    data object OnSave : GalleryAdminAction
    data object OnDismiss : GalleryAdminAction
}