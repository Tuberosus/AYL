package com.tuberosus.ayl.feature.home.advantages

sealed interface AdvantagesEvent {
    data object OnBackClick : AdvantagesEvent
}