package com.tuberosus.ayl.data.mapper

import com.tuberosus.ayl.data.remote.firestore.dto.StaffDto
import com.tuberosus.ayl.domain.model.staff.Staff

fun StaffDto.toStaff() = Staff(
    name = name,
    position = position,
    bio = bio,
    photoName = photoName,
    telegramLink = telegramLink
)