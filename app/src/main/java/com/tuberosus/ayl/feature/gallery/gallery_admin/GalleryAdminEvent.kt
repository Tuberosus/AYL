package com.tuberosus.ayl.feature.gallery.gallery_admin

sealed interface GalleryAdminEvent {
    data object SuccessSave : GalleryAdminEvent
    data class SaveErrorMessage(val message: String) : GalleryAdminEvent
}