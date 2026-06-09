package com.tuberosus.ayl.feature.news.news_admin

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.ui.components.layouts.AdminLayout
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsAdminScreenRoot(
    onDismiss: () -> Unit,
    newsForUpdate: News?,
    viewModel: NewsAdminViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(newsForUpdate) {
        viewModel.onAction(
            NewsAdminAction.SetNewsForUpdate(newsForUpdate)
        )
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewsAdminEvent.SuccessSave -> {
                onDismiss()
            }

            is NewsAdminEvent.SaveErrorMessage -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    NewsAdminScreen(
        state = state,
        isEditMode = newsForUpdate != null,
        onAction = viewModel::onAction,
        onDismiss = onDismiss,
    )
}

@Composable
fun NewsAdminScreen(
    state: NewsAdminState,
    isEditMode: Boolean,
    onAction: (NewsAdminAction) -> Unit,
    onDismiss: () -> Unit,
) {
    AdminLayout(
        canAction = state.canSave,
        isAction = state.isSaving,
        onDismiss = {
            onDismiss()
            onAction(NewsAdminAction.OnDismiss)
        },
        onAction = {
            onAction(NewsAdminAction.OnSave)
        },
    ) {
        Text(
            text = if (isEditMode) {
                "Редактирование новости"
            } else {
                "Добавить новость"
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "ОСНОВНАЯ ИНФОРМАЦИЯ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = state.newsDraft.title,
            onValueChange = { onAction(NewsAdminAction.OnTitleChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Заголовок",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.newsDraft.photoUrl,
            onValueChange = { onAction(NewsAdminAction.OnPhotoUrlChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Ссылка на изображение",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.newsDraft.sourceUrl,
            onValueChange = { onAction(NewsAdminAction.OnSourceUrlChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Ссылка на источник (подробнее)",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "ТЕКСТ НОВОСТИ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = state.newsDraft.newsText,
            onValueChange = { onAction(NewsAdminAction.OnNewsTextChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            placeholder = {
                Text(
                    text = "Основное содержание",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            shape = RoundedCornerShape(14.dp),
            maxLines = 8
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsAdminScreenPreview() {
    AYLTheme {
        NewsAdminScreen(
            state = NewsAdminState(),
            isEditMode = true,
            onAction = {},
            onDismiss = {}
        )
    }
}