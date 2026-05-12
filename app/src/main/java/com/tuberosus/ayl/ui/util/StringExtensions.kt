package com.tuberosus.ayl.ui.util

fun String.withNoBreakShortWords(): String {
    return replace(Regex("""\b([А-Яа-яA-Za-z]{1,2})\s""")) {
        "${it.groupValues[1]}\u00A0"
    }
}