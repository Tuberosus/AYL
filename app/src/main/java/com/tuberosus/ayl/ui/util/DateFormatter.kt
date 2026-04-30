package com.tuberosus.ayl.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale.forLanguageTag("ru"))
    return formatter.format(date)
}