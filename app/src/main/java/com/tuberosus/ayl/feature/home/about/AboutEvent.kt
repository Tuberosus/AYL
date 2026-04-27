package com.tuberosus.ayl.feature.home.about

sealed interface AboutEvent {
    data object OnAdvantagesClick : AboutEvent
    data object OnDocumentsClick : AboutEvent
}