package com.tuberosus.ayl.feature.home.documents

sealed interface DocumentsEvent {
    data object OnBackClick : DocumentsEvent
}