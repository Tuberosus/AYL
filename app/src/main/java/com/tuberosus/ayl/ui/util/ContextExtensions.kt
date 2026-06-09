package com.tuberosus.ayl.ui.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

fun Context.openUrl(url: String) {
    runCatching {
        startActivity(
            Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }.onFailure {
        Toast.makeText(
            this,
            "Невозможно открыть ссылку",
            Toast.LENGTH_SHORT
        ).show()
    }
}