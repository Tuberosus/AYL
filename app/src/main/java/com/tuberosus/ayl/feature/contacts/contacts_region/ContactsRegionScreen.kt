package com.tuberosus.ayl.feature.contacts.contacts_region

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.contacts.Region
import com.tuberosus.ayl.ui.components.layouts.TopBarWithBackLayout
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Pink
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactsRegionScreenRoot(
    onBackClick: () -> Unit,
    viewModel: ContactsRegionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ContactsRegionEvent.OnBackClick -> onBackClick()
            is ContactsRegionEvent.OnSocialMedialClick -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    event.link.toUri()
                )
                context.startActivity(intent)
            }
        }
    }

    ContactsRegionScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ContactsRegionScreen(
    state: ContactsRegionState,
    onAction: (ContactsRegionAction) -> Unit
) {
    TopBarWithBackLayout(
        title = "АЮЛ в регионах",
        onBackClick = { onAction(ContactsRegionAction.OnBackClick) }
    ) {
        Text(
            text = "Наша ассоциация проводит мероприятия по всей России. Узнать о событиях в регионах вы можете в социальных сетях!",
            style = MaterialTheme.typography.bodyLarge,
        )

        state.regions.forEach { region ->
            Spacer(modifier = Modifier.height(24.dp))
            RegionContactItem(
                region = region,
                onSocialClick = { onAction(ContactsRegionAction.OnSocialMedialClick(it)) }
            )
        }
    }
}

@Composable
private fun RegionContactItem(
    region: Region,
    onSocialClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null
        )
        Column {
            Text(
                text = region.name
            )
            Spacer(modifier = Modifier.height(4.dp))
            region.contacts.telegram?.let {
                TextLink(
                    onClick = { onSocialClick(it) },
                    text = "Телеграм-канал"
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            region.contacts.site?.let {
                TextLink(
                    onClick = { onSocialClick(it) },
                    text = "Сайт"
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            region.contacts.vk?.let {
                TextLink(
                    onClick = { onSocialClick(it) },
                    text = "Вконтакте"
                )
            }
        }
    }
}

@Composable
private fun TextLink(
    onClick: () -> Unit,
    text: String
) {
    Text(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        text = text,
        color = Pink
    )
}

@Preview(showBackground = true)
@Composable
private fun ContactsRegionScreenPreview() {
    AYLTheme {
        ContactsRegionScreen(
            state = ContactsRegionState(),
            onAction = {}
        )
    }
}