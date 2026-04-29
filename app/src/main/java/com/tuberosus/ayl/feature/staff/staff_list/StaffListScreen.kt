package com.tuberosus.ayl.feature.staff.staff_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.ui.components.FullScreenProgressBar
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Green
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffListScreenRoot(
    viewModel: StaffListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StaffListScreen(
        state = state
    )
}

@Composable
private fun StaffListScreen(
    state: StaffListState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 36.dp,
            )
    ) {
        TitleWithUnderline("Штат АЮЛ")
        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading ->
                FullScreenProgressBar()

            state.staff.isNullOrEmpty().not() ->
                StaffColumn(state.staff)
        }
    }
}

@Composable
private fun StaffColumn(staff: List<Staff>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(
            items = staff,
            key = { it.name }
        ) { item ->
            StaffItem(item)
        }
    }
}

@Composable
private fun StaffItem(staffItem: Staff) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(staffItem.photoName)
        .size(80, 80)
        .diskCachePolicy(CachePolicy.DISABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Green,
                    shape = CircleShape
                ),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ayl_logo_placeholder),
            error = painterResource(R.drawable.ayl_logo_placeholder),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(
                    text = staffItem.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(R.drawable.ic_telegram),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = staffItem.position,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    lineHeight = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = staffItem.bio,
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StaffListScreenPreview() {
    AYLTheme {
        StaffListScreen(
            state = StaffListState(
                isLoading = false,
                staff = listOf(
                    Staff(
                        name = "Иван Иванов",
                        position = "Старший разработчик",
                        bio = "Опыт 10 лет, эксперт в Compose",
                        photoName = "ivan.jpg",
                        telegramLink = "https://t.me/ivan"
                    ),
                    Staff(
                        name = "Иван Иванов",
                        position = "Старший разработчик Старший разработчик",
                        bio = "Опыт 10 лет, эксперт в Compose",
                        photoName = "ivan.jpg",
                        telegramLink = "https://t.me/ivan"
                    )
                )
            )
        )
    }
}