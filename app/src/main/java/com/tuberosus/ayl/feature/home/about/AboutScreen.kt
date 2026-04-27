package com.tuberosus.ayl.feature.home.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.components.BulletList
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Green
import com.tuberosus.ayl.ui.theme.Pink
import com.tuberosus.ayl.ui.theme.White
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(24.dp)
                .size(120.dp)
                .align(alignment = Alignment.CenterHorizontally),
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
        )
        AboutContent()
        Spacer(modifier = Modifier.height(16.dp))
        AdvantagesLine(
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
    TitleWithUnderline("О нас")

    Spacer(modifier = Modifier.height(16.dp))

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

@Composable
private fun AdvantagesLine(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
            onClick = { onClick() },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "Почему выбирают нас?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Pink
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward_ios),
            contentDescription = null,
            tint = Pink
        )
    }
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
    Button(
        modifier = Modifier
            .heightIn(min = 52.dp)
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green,
            contentColor = White
        )
    ) {
        Text(
            text = "Документы",
            style = MaterialTheme.typography.titleMedium,
        )
    }
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