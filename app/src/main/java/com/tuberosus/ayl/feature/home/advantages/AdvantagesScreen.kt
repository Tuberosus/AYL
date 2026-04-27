package com.tuberosus.ayl.feature.home.advantages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.components.BackTopAppBar
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Blue
import com.tuberosus.ayl.ui.theme.Green
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

            Spacer(modifier = Modifier.height(36.dp))

            AdvantagesExpandableSection()
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

@Composable
private fun AdvantagesExpandableSection() {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val items = listOf(
        "обучаем «Soft Skills» в реальном времени на практике, а не на словах",
        "рассказываем про основы лидерства, знание которых раскрывает понимание потенциала и механизмов инструментов коммуникации и пр.",
        "помогаем отработать навыки разрешения конфликтов",
        "даем знания и пространство для практики навыков публичных выступлений",
        "формируем условия для полного погружения в командную работу",
        "помогаем развивать креативное мышление",
        "обучаем принципам обратной связи",
        "работаем по авторской программе, созданной командой преподавателей, психологов и бизнес-коучей, которые собрали лучшие практики и адаптировали каждую под молодежь"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Чем программа АЮЛ отличается от остальных?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.ic_keyboard_arrow_up),
                contentDescription = null,
                tint = Blue,
                modifier = Modifier
                    .rotate(
                        animateFloatAsState(
                            targetValue = if (expanded) 0f else 180f,
                            label = ""
                        ).value
                    )
            )
        }

        AnimatedVisibility(
            visible = expanded
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp),
            ) {
                items.forEach { text ->
                    StarBulletItem(text)
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
fun StarBulletItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            modifier = Modifier
                .offset(y = (-8).dp),
            text = "★",
            color = Green,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
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