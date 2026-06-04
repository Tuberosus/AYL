package com.tuberosus.ayl.feature.admin

sealed interface AuthAction {
    data class OnEmailChange(val value: String) : AuthAction
    data class OnPasswordChange(val value: String) : AuthAction
    data object OnPasswordVisibleClick : AuthAction
    data object OnLogInClick : AuthAction
    data object OnSingOut : AuthAction
    data object ClearInput : AuthAction
}