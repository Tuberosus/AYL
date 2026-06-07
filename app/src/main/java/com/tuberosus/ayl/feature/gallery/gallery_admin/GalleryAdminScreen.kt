package com.tuberosus.ayl.feature.gallery.gallery_admin

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.ui.components.layouts.AdminLayout
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GalleryAdminRoot(
    onDismiss: () -> Unit,
    photoForUpdate: GalleryPhoto?,
    viewModel: GalleryAdminViewModel = koinViewModel(
        parameters = {
            parametersOf(photoForUpdate)
        }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is GalleryAdminEvent.SuccessSave -> {
                onDismiss()
            }

            is GalleryAdminEvent.SaveErrorMessage -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    GalleryAdminScreen(
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
        onDismiss = {
            onDismiss()
            viewModel.onAction(GalleryAdminAction.OnDismiss)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryAdminScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    photoLinks: String,
    onPhotoLinksChange: (String) -> Unit,
    canSave: Boolean,
    isSaving: Boolean,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
) {
    AdminLayout(
        canAction = canSave,
        isAction = isSaving,
        onAction = onSave,
        onDismiss = onDismiss
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
            label = {
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
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryAdminScreenPreview() {
    AYLTheme {
        GalleryAdminScreen(
            title = "",
            onTitleChange = {},
            photoLinks = "",
            onPhotoLinksChange = {},
            canSave = true,
            isSaving = false,
            onSave = {},
            onDismiss = {}
        )
    }
}