package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.util.Result

interface GalleryRepository {
    suspend fun getGalleryPhotos(): Result<List<GalleryPhoto>>

    suspend fun savePhotoToGallery(galleryPhoto: GalleryPhoto): Result<String>

    suspend fun deletePhotoToGallery(galleryPhotoId: String): Result<Unit>
}