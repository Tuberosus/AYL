package com.tuberosus.ayl.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuberosus.ayl.domain.util.AppError

@Composable
fun ErrorView(error: AppError) {
    val message = when (error) {
        AppError.Network -> "Ошибка загрузки данных"
        AppError.Timeout -> "Сервер не отвечает"
        AppError.Permission -> "Нет доступа"
        AppError.NotFound -> "Ничего не найдено"
        is AppError.Unknown -> "Ошибка: ${error.message}"
    }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-50).dp),
            text = message,
            style = MaterialTheme.typography.labelLarge,
            fontSize = 18.sp
        )
    }
}