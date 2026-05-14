package com.tuberosus.ayl.ui.components.layouts

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuberosus.ayl.domain.util.AppError
import com.tuberosus.ayl.ui.theme.Green
import com.tuberosus.ayl.ui.theme.White

@Composable
fun ErrorView(
    error: AppError,
    onRetryClick: () -> Unit = {}
) {
    val message = when (error) {
        AppError.Network -> "Ошибка загрузки данных"
        AppError.Timeout -> "Сервер не отвечает"
        AppError.Permission -> "Нет доступа"
        AppError.NotFound -> "Ничего не найдено"
        is AppError.Unknown -> {
            Log.e("AppError", "error: ${error.message}")
            "Что-то пошло не так"
        }
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
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                contentColor = White
            ),
            onClick = onRetryClick
        ) {
            Text(
                text = "Повторить",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}