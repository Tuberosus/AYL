package com.tuberosus.ayl.feature.home.documents

sealed interface DocumentsAction {
    data object OnBackClick : DocumentsAction
}
