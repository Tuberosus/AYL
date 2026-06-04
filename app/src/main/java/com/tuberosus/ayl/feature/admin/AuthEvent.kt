package com.tuberosus.ayl.feature.admin

sealed interface AuthEvent {
    data class InfoMessage(val message: String) : AuthEvent
    data object ExitForm : AuthEvent
}