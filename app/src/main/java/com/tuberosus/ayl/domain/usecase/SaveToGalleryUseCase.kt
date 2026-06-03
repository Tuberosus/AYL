package com.tuberosus.ayl.domain.usecase

import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.repository.GalleryRepository
import com.tuberosus.ayl.domain.util.Result

class SaveToGalleryUseCase(
    private val galleryRepository: GalleryRepository
) {
    suspend operator fun invoke(galleryPhotos: List<GalleryPhoto>): Result<Unit> {
        return galleryRepository.savePhotosToGallery(galleryPhotos)
    }
}