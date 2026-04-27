package com.tuberosus.ayl.feature.home.documents

sealed interface DocumentsAction {
    data object OnAdvantagesClick : DocumentsAction
    data object OnDocumentsClick : DocumentsAction
}