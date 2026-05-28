package com.tuberosus.ayl.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.tuberosus.ayl.domain.model.auth.User

fun FirebaseUser.toUser() = User(
    uid = uid,
    email = email,
)