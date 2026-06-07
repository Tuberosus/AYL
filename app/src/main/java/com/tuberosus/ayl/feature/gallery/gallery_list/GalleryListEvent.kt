package com.tuberosus.ayl.feature.gallery.gallery_list

sealed interface GalleryListEvent {
    data class InfoMessage(val message: String) : GalleryListEvent
}