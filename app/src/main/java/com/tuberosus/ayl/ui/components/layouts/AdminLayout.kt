package com.tuberosus.ayl.ui.components.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.ui.theme.AYLTheme

@Composable
fun AdminLayout(
    canSave: Boolean,
    isSaving: Boolean,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp)
    ) {
        content()
        Spacer(Modifier.height(24.dp))
        RowButtons(
            canSave = canSave,
            isSaving = isSaving,
            onSave = onSave,
            onDismiss = onDismiss,
        )
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun RowButtons(
    canSave: Boolean,
    isSaving: Boolean,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedButton(
            onClick = onDismiss,
            modifier = Modifier.weight(1f)
        ) {
            Text("Отмена")
        }

        Button(
            onClick = onSave,
            enabled = canSave,
            modifier = Modifier.weight(1f)
        ) {
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Сохранить")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminLayoutPreview() {
    AYLTheme {
        AdminLayout(
            canSave = false,
            isSaving = false,
            onSave = {},
            onDismiss = {},
        ) {}
    }
}