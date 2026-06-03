package com.tuberosus.ayl.feature.gallery.gallery_admin

data class GalleryAdminState(
    val eventTitle: String = "",
    val photoLinks: List<String> = emptyList(),
    val isSaving: Boolean = false,
    val canSave: Boolean = false,
)