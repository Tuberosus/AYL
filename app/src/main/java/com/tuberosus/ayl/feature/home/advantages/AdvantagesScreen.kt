package com.tuberosus.ayl.feature.home.advantages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuberosus.ayl.ui.components.BackTopAppBar
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Blue
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdvantagesScreenRoot(
    onBackClick: () -> Unit,
    viewModel: AdvantagesViewModel = koinViewModel(),
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AdvantagesEvent.OnBackClick -> onBackClick()
        }
    }
    AdvantagesScreen(
        onBackClick = {
            viewModel.onAction(AdvantagesAction.OnBackClick)
        }
    )
}

@Composable
private fun AdvantagesScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackTopAppBar(
            onBackClick = onBackClick
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            TitleWithUnderline("Почему выбирают нас?")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Мы разработали собственные подходы и методику, которая помогает участникам проходить обучение в комфортной атмосфере и развивать свои способности.",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            NumberLabel("1")

            Spacer(modifier = Modifier.height(12.dp))

            SectionBlock(
                title = "Молодые учат молодых",
                text = "Это одна из главных ценностей АЮЛ. Данный принцип позволяет организаторам и участникам говорить на одном языке, что способствует лучшему усвоению новых навыков и знаний."
            )

            Spacer(modifier = Modifier.height(36.dp))

            NumberLabel("2")

            Spacer(modifier = Modifier.height(12.dp))

            SectionBlock(
                title = "Полное погружение в процесс",
                text = "В отличие от других организаций, мы проводим конференции (5-дневные тренинги) на территории базы, на которой участники не только проходят обучение, но и живут в течение всего времени. Это позволяет погрузиться в рабочий процесс и, благодаря практическим частям нашей программы, попрактиковаться в изученном материале в режиме \"реального времени\"."
            )

            Spacer(modifier = Modifier.height(36.dp))

            NumberLabel("3")

            Spacer(modifier = Modifier.height(12.dp))

            SectionBlock(
                title = "Уникальная атмосфера",
                text = "Важной ценностью АЮЛ является атмосфера сотрудничества и безопасности. На наших " +
                        "мероприятиях каждый участник может свободно выражать свои мысли, зная, что он будет услышан. " +
                        "Такая теплая атмосфера остается надолго в сердце каждого, кто хоть раз побывал на нашем тренинге."
            )
        }
    }
}

@Composable
private fun NumberLabel(number: String) {
    Text(
        text = "-$number-",
        color = Blue,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun SectionBlock(
    title: String,
    text: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 34.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AdvantagesScreenPreview() {
    AYLTheme {
        AdvantagesScreen(
            onBackClick = {}
        )
    }
}