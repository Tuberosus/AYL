package com.tuberosus.ayl.domain.repository

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun observeGalleryPhotos(): Flow<Result<List<GalleryPhoto>>>
    suspend fun getGalleryPhotos(): Result<List<GalleryPhoto>>

    suspend fun savePhotosToGallery(galleryPhotos: List<GalleryPhoto>): Result<Unit>

    suspend fun deletePhotoFromGallery(galleryPhotoId: String): Result<Unit>
}