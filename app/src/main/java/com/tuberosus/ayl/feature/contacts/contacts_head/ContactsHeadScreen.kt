package com.tuberosus.ayl.feature.contacts.contacts_head

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.components.AylButton
import com.tuberosus.ayl.ui.components.AylClickableRow
import com.tuberosus.ayl.ui.components.AylLogo
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Blue
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactsHeadScreenRoot(
    onRegionsContactsClick: () -> Unit,
    onDonationClick: () -> Unit,
    viewModel: ContactsHeadViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ContactsHeadEvents.OnSiteClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_VIEW,
                    uriString = event.site
                )
            }

            is ContactsHeadEvents.OnEmailClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_SENDTO,
                    uriString = "mailto:${event.email}"
                )
            }

            is ContactsHeadEvents.OnPhoneClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_DIAL,
                    uriString = "tel:${event.phone}"
                )
            }

            is ContactsHeadEvents.OnTelegramClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_VIEW,
                    uriString = event.link
                )
            }

            is ContactsHeadEvents.OnYoutubeClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_VIEW,
                    uriString = event.link
                )
            }

            is ContactsHeadEvents.OnVkClick -> {
                startActivityIntent(
                    context = context,
                    action = Intent.ACTION_VIEW,
                    uriString = event.link
                )
            }

            is ContactsHeadEvents.OnRegionsContactsClick -> onRegionsContactsClick()
            is ContactsHeadEvents.OnDonationClick -> onDonationClick()
        }
    }

    ContactsHeadScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

private fun startActivityIntent(
    context: Context,
    action: String,
    uriString: String
) {
    val intent = Intent(
        action,
        uriString.toUri()
    )
    context.startActivity(intent)
}

@Composable
private fun ContactsHeadScreen(
    state: ContactsHeadState,
    onAction: (ContactsHeadAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {
        AylLogo()
        TitleWithUnderline(
            text = "Контакты"
        )
        Spacer(modifier = Modifier.height(12.dp))
        ContactItem(
            title = "Сайт",
            value = state.contacts.site,
            onClick = { onAction(ContactsHeadAction.OnSiteClick) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContactItem(
            title = "Электронная почта",
            value = state.contacts.email,
            onClick = { onAction(ContactsHeadAction.OnEmailClick) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContactItem(
            title = "Телефон",
            value = state.contacts.phone,
            onClick = { onAction(ContactsHeadAction.OnPhoneClick) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Исполнительный директор\nАлёна Коваленко"
        )
        Spacer(modifier = Modifier.height(24.dp))
        SocialMediaRow(
            onAction = onAction
        )
        Spacer(modifier = Modifier.height(24.dp))
        AylClickableRow(
            title = "АЮЛ в регионах",
            onClick = { onAction(ContactsHeadAction.OnRegionsContactsClick) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        AylButton(
            title = "Поддержать нас",
            onClick = { onAction(ContactsHeadAction.OnDonationClick) }
        )
    }

}

@Composable
private fun ContactItem(
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            text = "$title:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
        )
        Text(
            modifier = Modifier.clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Blue
        )
    }
}

@Composable
private fun SocialMediaRow(
    onAction: (ContactsHeadAction) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        IconButton(
            onClick = { onAction(ContactsHeadAction.OnTelegramClick) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_telegram),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        IconButton(
            onClick = { onAction(ContactsHeadAction.OnYoutubeClick) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_youtube),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        IconButton(
            onClick = { onAction(ContactsHeadAction.OnVkClick) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_vk),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactsHeadScreenPreview() {
    AYLTheme {
        ContactsHeadScreen(
            state = ContactsHeadState(),
            onAction = {}
        )
    }
}