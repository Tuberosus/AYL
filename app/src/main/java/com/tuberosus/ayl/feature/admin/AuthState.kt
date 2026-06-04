package com.tuberosus.ayl.feature.admin

data class AuthState(
    val email: String = "",
    val password: String = "",
    val canLogIn: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggedIn: Boolean = false,
)
