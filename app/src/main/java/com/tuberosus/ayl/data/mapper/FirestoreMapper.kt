package com.tuberosus.ayl.data.mapper

import com.google.firebase.Timestamp
import com.tuberosus.ayl.data.remote.firestore.dto.GalleryPhotoDto
import com.tuberosus.ayl.data.remote.firestore.dto.NewsDto
import com.tuberosus.ayl.data.remote.firestore.dto.StaffDto
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.model.staff.Staff
import java.util.Date

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

fun News.toNewsDto() = NewsDto(
    id = id,
    title = title,
    content = content,
    imageUrl = imageUrl,
    date = if (date != 0L) Timestamp(Date(date)) else null,
    linkUrl = linkUrl,
)

fun GalleryPhotoDto.toGalleryPhoto() = GalleryPhoto(
    id = id,
    imageName = imageName,
    title = title
)

fun GalleryPhoto.toGalleryPhotoDto() = GalleryPhotoDto(
    id = id,
    imageName = imageName,
    title = title,
)