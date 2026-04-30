package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.util.Result

interface GalleryRepository {
    suspend fun getGalleryPhotos(): Result<List<GalleryPhoto>>
}