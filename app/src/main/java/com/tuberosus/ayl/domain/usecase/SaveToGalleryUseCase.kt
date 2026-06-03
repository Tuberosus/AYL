package com.tuberosus.ayl.domain.usecase

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.repository.GalleryRepository

class SaveToGalleryUseCase(
    private val galleryRepository: GalleryRepository
) {
    suspend fun invoke(galleryPhoto: GalleryPhoto) {
        galleryRepository.savePhotoToGallery(galleryPhoto)
    }
}