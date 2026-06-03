package com.tuberosus.ayl.feature.gallery.gallery_admin

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryAdminRoot(
    viewModel: GalleryAdminViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GalleryAdmin(
        title = state.eventTitle,
        onTitleChange = {
            viewModel.onAction(GalleryAdminAction.OnTitleChange(it))
        },
        photoLinks = state.photoLinks.joinToString("\n"),
        onPhotoLinksChange = {
            viewModel.onAction(GalleryAdminAction.OnPhotoLinkChange(it))
        },
        canSave = state.canSave,
        isSaving = state.isSaving,
        onSave = {
            viewModel.onAction(GalleryAdminAction.OnSave)
        },
        onDismiss = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryAdmin(
    title: String,
    onTitleChange: (String) -> Unit,
    photoLinks: String,
    onPhotoLinksChange: (String) -> Unit,
    canSave: Boolean,
    isSaving: Boolean,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 12.dp)
    ) {

        Text(
            text = "Загрузка фотографий",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "МЕРОПРИЯТИЕ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Название (например: Конференция 2026)",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "ССЫЛКИ НА ФОТО (каждая с новой строки)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = photoLinks,
            onValueChange = onPhotoLinksChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(14.dp),
            maxLines = 8
        )

        Spacer(Modifier.height(24.dp))

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

        Spacer(Modifier.height(12.dp))
    }
}