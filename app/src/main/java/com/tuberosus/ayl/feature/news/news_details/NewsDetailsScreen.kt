package com.tuberosus.ayl.feature.news.news_details

import android.content.Intent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.components.layouts.ErrorView
import com.tuberosus.ayl.ui.components.layouts.FullScreenProgressBar
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Pink
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import com.tuberosus.ayl.ui.util.formatDate
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailsScreenRoot(
    onBackClick: () -> Unit,
    viewModel: NewsDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is NewsDetailsEvent.OnEventLinkClick -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    event.link.toUri()
                )
                context.startActivity(intent)
            }
        }
    }

    NewsDetailsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is NewsDetailsAction.OnBackClick ->
                    onBackClick()

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsScreen(
    state: NewsDetailsState,
    onAction: (NewsDetailsAction) -> Unit,
) {
    val scrollState = rememberScrollState()
    val progress = (scrollState.value / 120f).coerceIn(0f, 1f)

    val appBarColor = lerp(
        Color.Transparent,
        MaterialTheme.colorScheme.background,
        progress
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading ->
                FullScreenProgressBar()

            state.error != null ->
                ErrorView(state.error)

            state.news != null ->
                NewsDetailsContent(
                    news = state.news,
                    scrollState = scrollState,
                    onMoreClick = { link ->
                        onAction(
                            NewsDetailsAction.OnEventLinkClick(link)
                        )
                    }
                )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBarColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onAction(NewsDetailsAction.OnBackClick) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_ios),
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
    }
}

@Composable
private fun NewsDetailsContent(
    news: News,
    scrollState: ScrollState,
    onMoreClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = news.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ayl_logo_placeholder),
            error = painterResource(R.drawable.ayl_logo_placeholder),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            TitleWithUnderline(
                text = news.title
            )
            DateWithIcon(news.date)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    lineHeight = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(28.dp))

            if (news.linkUrl.isNotBlank()) {
                MoreLink(
                    onMoreClick = { onMoreClick(news.linkUrl) }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun DateWithIcon(timestamp: Long) {
    val color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
            tint = color
        )
        Text(
            text = formatDate(timestamp),
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
            color = color,
        )
    }
}

@Composable
private fun MoreLink(
    onMoreClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .clickable(
                onClick = onMoreClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
        shape = RoundedCornerShape(8.dp),
        color = Pink.copy(alpha = 0.2f),
        contentColor = Pink
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Подробнее",
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                modifier = Modifier
                    .size(16.dp),
                painter = painterResource(R.drawable.ic_outbound),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailsScreenPreview() {
    AYLTheme {
        NewsDetailsScreen(
            state = NewsDetailsState(
                isLoading = false,
                news = News(
                    id = "1",
                    title = "Новый рекорд скорости",
                    content = "Электромобиль разогнался до 500 км/ч на испытательном треке.",
                    imageUrl = "https://example.com/images/speed_record.jpg",
                    date = 1709251200000, // 1 марта 2024 г.
                    linkUrl = "123"
                )
            ),
            onAction = {}
        )
    }
}