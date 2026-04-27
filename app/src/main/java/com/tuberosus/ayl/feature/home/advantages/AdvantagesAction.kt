package com.tuberosus.ayl.feature.home.advantages

sealed interface AdvantagesAction {
    data object OnBackClick : AdvantagesAction
}