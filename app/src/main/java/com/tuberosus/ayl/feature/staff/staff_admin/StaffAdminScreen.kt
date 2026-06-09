package com.tuberosus.ayl.feature.staff.staff_admin

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
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.ui.components.layouts.AdminLayout
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffAdminRoot(
    staffForUpdate: Staff?,
    onDismiss: () -> Unit,
    viewModel: StaffAdminViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(staffForUpdate) {
        viewModel.onAction(
            StaffAdminAction.SetStaffForUpdate(staffForUpdate)
        )
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is StaffAdminEvent.SuccessSave -> {
                onDismiss()
            }

            is StaffAdminEvent.SaveErrorMessage -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    StaffAdminScreen(
        state = state,
        isEditMode = staffForUpdate != null,
        onAction = viewModel::onAction,
        onDismiss = onDismiss
    )
}

@Composable
fun StaffAdminScreen(
    state: StaffAdminState,
    isEditMode: Boolean,
    onAction: (StaffAdminAction) -> Unit,
    onDismiss: () -> Unit,
) {
    AdminLayout(
        canAction = state.canSave,
        isAction = state.isSaving,
        onDismiss = {
            onDismiss()
            onAction(StaffAdminAction.OnDismiss)
        },
        onAction = {
            onAction(StaffAdminAction.OnSave)
        }
    ) {
        Text(
            text = if (isEditMode) {
                "Редактирование участника"
            } else {
                "Новый участник"
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.staffDraft.name,
            onValueChange = { onAction(StaffAdminAction.OnNameChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "ФИО",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.staffDraft.position,
            onValueChange = { onAction(StaffAdminAction.OnPositionChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Должность",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.staffDraft.photoUrl,
            onValueChange = { onAction(StaffAdminAction.OnPhotoUrlChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Ссылка на фото (Direct link)",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.staffDraft.telegramLink,
            onValueChange = { onAction(StaffAdminAction.OnTelegramLinkChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Ссылка на Telegram",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = state.staffDraft.bio,
            onValueChange = { onAction(StaffAdminAction.OnBioChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Несколько слов об АЮЛ",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AYLTheme {
        StaffAdminScreen(
            state = StaffAdminState(),
            isEditMode = true,
            onAction = {},
            onDismiss = {}
        )
    }
}