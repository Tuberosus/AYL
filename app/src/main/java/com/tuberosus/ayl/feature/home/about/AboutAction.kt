package com.tuberosus.ayl.feature.home.about

sealed interface AboutAction {
    data object OnAdvantagesClick : AboutAction
    data object OnDocumentsClick : AboutAction
}