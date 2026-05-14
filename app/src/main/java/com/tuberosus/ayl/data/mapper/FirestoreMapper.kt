package com.tuberosus.ayl.data.mapper

import com.google.firebase.firestore.FirebaseFirestoreException
import com.tuberosus.ayl.data.remote.firestore.dto.GalleryPhotoDto
import com.tuberosus.ayl.data.remote.firestore.dto.NewsDto
import com.tuberosus.ayl.data.remote.firestore.dto.StaffDto
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.util.AppError

fun StaffDto.toStaff() = Staff(
    name = name,
    position = position,
    bio = bio,
    photoName = photoName,
    telegramLink = telegramLink
)

fun NewsDto.toNews() = News(
    id = id,
    title = title,
    content = content,
    imageUrl = imageUrl,
    date = date?.toDate()?.time ?: 0L,
    linkUrl = linkUrl,
)

fun GalleryPhotoDto.toGalleryPhoto() = GalleryPhoto(
    id = id,
    imageName = imageName,
    title = title
)

fun Throwable.toAppError(): AppError {
    return when (this) {
        is FirebaseFirestoreException -> {
            when (code) {
                FirebaseFirestoreException.Code.UNAVAILABLE ->
                    AppError.Network

                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED ->
                    AppError.Timeout

                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    AppError.Permission

                FirebaseFirestoreException.Code.NOT_FOUND ->
                    AppError.NotFound

                else -> AppError.Unknown(message)
            }
        }
        else -> AppError.Unknown(message)
    }
}