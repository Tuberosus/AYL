package com.tuberosus.ayl.feature.home.about

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.ui.components.AylButton
import com.tuberosus.ayl.ui.components.AylClickableRow
import com.tuberosus.ayl.ui.components.BulletList
import com.tuberosus.ayl.ui.components.layouts.LogoLayout
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreenRoot(
    onAdvantagesClick: () -> Unit,
    onDocumentsClick: () -> Unit,
    viewModel: AboutViewModel = koinViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AboutEvent.OnAdvantagesClick -> onAdvantagesClick()
            is AboutEvent.OnDocumentsClick -> onDocumentsClick()
        }
    }

    AboutScreen(
        onAdvantagesClick = {
            viewModel.onAction(AboutAction.OnAdvantagesClick)
        },
        onDocumentsClick = {
            viewModel.onAction(AboutAction.OnDocumentsClick)
        }
    )
}

@Composable
private fun AboutScreen(
    onAdvantagesClick: () -> Unit,
    onDocumentsClick: () -> Unit,
) {
    LogoLayout(
        title = "О нас"
    ) {
        AboutContent()
        Spacer(modifier = Modifier.height(16.dp))
        AylClickableRow(
            title = "Почему выбирают нас?",
            onClick = onAdvantagesClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        DocumentsButtonWithDescription(
            onClick = onDocumentsClick
        )
    }
}

@Composable
private fun AboutContent() {
    Text(
        text = "Мы молодёжная некоммерческая организация",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Проводим лидерские тренинги для молодёжи более 30 лет. " +
                "Обучение проходит по авторским программам АЮЛ®",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Проводим тренинги в разных форматах:",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    BulletList(
        items = listOf(
            "Конференции — пятидневные тренинги",
            "Однодневные тренинги"
        )
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Обучение проходит в формате:",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    BulletList(
        items = listOf(
            "малых групп по 6–12 человек, у каждой есть ведущий, который сопровождает группу",
            "общих сессий — лекции и тренинги для всех участников"
        )
    )
}

@Composable fun DocumentsButtonWithDescription(
    onClick: () -> Unit,
) {
    Text(
        text = buildAnnotatedString {
            append("Ознакомится с краткой информацией об организации и учредительными документами вы можете по ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("кнопке ниже")
            }
        },
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
    )
    Spacer(modifier = Modifier.height(8.dp))
    AylButton(
        onClick = onClick,
        title = "Документы"
    )
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    AYLTheme {
        AboutScreen(
            onAdvantagesClick = {},
            onDocumentsClick = {}
        )
    }
}