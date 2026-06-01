package com.tuberosus.ayl.data.repository

import com.tuberosus.ayl.data.mapper.toGalleryPhoto
import com.tuberosus.ayl.data.mapper.toGalleryPhotoDto
import com.tuberosus.ayl.data.remote.firestore.FirestoreRemoteDataSource
import com.tuberosus.ayl.data.remote.firestore.dto.GalleryPhotoDto
import com.tuberosus.ayl.data.remote.firestore.getCollection
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.repository.GalleryRepository
import com.tuberosus.ayl.domain.util.Result
import com.tuberosus.ayl.domain.util.map

class GalleryRepositoryImpl(
    private val firestoreRemoteDataSource: FirestoreRemoteDataSource
) : GalleryRepository {
    private var cachedGallery: List<GalleryPhoto>? = null

    override suspend fun getGalleryPhotos(): Result<List<GalleryPhoto>> {
        cachedGallery?.let {
            return Result.Success(it)
        }

        return firestoreRemoteDataSource.getCollection<GalleryPhotoDto>(GALLERY_COLLECTION)
            .map { galleryPhotoDtos ->
                val galleryPhoto = galleryPhotoDtos
                    .map { it.toGalleryPhoto() }
                cachedGallery = galleryPhoto
                galleryPhoto
            }
    }

    override suspend fun savePhotoToGallery(galleryPhoto: GalleryPhoto): Result<String> {
        return firestoreRemoteDataSource.saveDocument(
            collection = GALLERY_COLLECTION,
            data = galleryPhoto.toGalleryPhotoDto()
        )
    }

    override suspend fun deletePhotoToGallery(galleryPhotoId: String): Result<Unit> {
        return firestoreRemoteDataSource.deleteDocument(
            collection = GALLERY_COLLECTION,
            documentId = galleryPhotoId
        )
    }

    companion object {
        private const val GALLERY_COLLECTION = "Gallery"
    }
}