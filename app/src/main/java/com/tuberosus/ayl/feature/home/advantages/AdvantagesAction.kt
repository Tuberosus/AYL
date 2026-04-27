package com.tuberosus.ayl.feature.home.advantages

sealed interface AdvantagesAction {
    data object OnAdvantagesClick : AdvantagesAction
    data object OnDocumentsClick : AdvantagesAction
}