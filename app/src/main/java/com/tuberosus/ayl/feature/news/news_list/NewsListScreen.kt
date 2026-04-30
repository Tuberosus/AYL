package com.tuberosus.ayl.feature.news.news_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.ui.components.ErrorView
import com.tuberosus.ayl.ui.components.FullScreenProgressBar
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Pink
import com.tuberosus.ayl.ui.util.formatDate
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsListScreenRoot(
    viewModel: NewsListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NewsListScreen(
        state = state
    )
}

@Composable
private fun NewsListScreen(
    state: NewsListState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp,
            )
    ) {
        TitleWithUnderline("Штат АЮЛ")
        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading ->
                FullScreenProgressBar()

            state.error != null ->
                ErrorView(state.error)

            state.news != null ->
                NewsColumn(state.news)
        }
    }
}

@Composable
private fun NewsColumn(news: List<News>) {
    LazyColumn {
        items(
            items = news,
            key = { it.date }
        ) { item ->
            NewsCard(item)
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun NewsCard(newsItem: News) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                model = newsItem.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ayl_logo_placeholder),
                error = painterResource(R.drawable.ayl_logo_placeholder),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    lineHeight = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.6f
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = formatDate(newsItem.date),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward_ios),
                    contentDescription = null,
                    tint = Pink
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsListScreenPreview() {
    AYLTheme {
        NewsListScreen(
            state = NewsListState(
                isLoading = false,
                news = listOf(
                    News(
                        title = "Запуск нового спутника",
                        content = "Компания успешно вывела на орбиту спутник связи нового поколения. вывела на орбиту спутник связи нового поколения.",
                        imageUrl = "https://example.com/images/satellite.jpg",
                        date = 1704067200000 // 1 января 2024 г.
                    ),
                    News(
                        title = "Открытие фестиваля AI",
                        content = "В Москве стартовал международный фестиваль искусственного интеллекта.",
                        imageUrl = "https://example.com/images/ai_fest.jpg",
                        date = 1706745600000 // 1 февраля 2024 г.
                    ),
                    News(
                        title = "Новый рекорд скорости",
                        content = "Электромобиль разогнался до 500 км/ч на испытательном треке.",
                        imageUrl = "https://example.com/images/speed_record.jpg",
                        date = 1709251200000 // 1 марта 2024 г.
                    )
                )
            )
        )
    }
}