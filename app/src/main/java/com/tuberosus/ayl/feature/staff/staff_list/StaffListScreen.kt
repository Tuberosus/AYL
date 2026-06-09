package com.tuberosus.ayl.feature.staff.staff_list

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import coil3.compose.SubcomposeAsyncImage
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.util.AppError
import com.tuberosus.ayl.feature.admin.AdminMenuBottomSheet
import com.tuberosus.ayl.ui.components.PlaceholderImage
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.components.layouts.ErrorView
import com.tuberosus.ayl.ui.components.layouts.FullScreenProgressBar
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Green
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import com.tuberosus.ayl.ui.util.openUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffListScreenRoot(
    isLoggedIn: Boolean,
    onStaffEdit: (Staff?) -> Unit,
    viewModel: StaffListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is StaffListEvent.OnTgClick -> {
                context.openUrl(event.telegram)
            }

            is StaffListEvent.InfoMessage -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    StaffListScreen(
        state = state,
        isLongClickEnable = isLoggedIn,
        onAction = viewModel::onAction
    )

    if (state.isEditMenuOpen && isLoggedIn) {
        AdminMenuBottomSheet(
            onDeleteClick = {
                viewModel.onAction(
                    StaffListAction.OnDeleteClick
                )
            },
            onEditClick = {
                viewModel.onAction(
                    StaffListAction.OnDismissClick
                )
                onStaffEdit(state.selectedStaff)
            },
            onDismiss = {
                viewModel.onAction(
                    StaffListAction.OnDismissClick
                )
            }
        )
    }
}

@Composable
private fun StaffListScreen(
    state: StaffListState,
    isLongClickEnable: Boolean,
    onAction: (StaffListAction) -> Unit,
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
        Spacer(modifier = Modifier.height(4.dp))

        when {
            state.isLoading ->
                FullScreenProgressBar()

            state.staff != null ->
                StaffColumn(
                    staff = state.staff,
                    onTgClick = {
                        onAction(
                            StaffListAction.OnTgClick(it)
                        )
                    },
                    isLongClickEnable = isLongClickEnable,
                    onLongClick = {
                        onAction(
                            StaffListAction.OnStaffLongClick(it)
                        )
                    }
                )

            state.error != null ->
                ErrorView(state.error)
        }
    }
}

@Composable
private fun StaffColumn(
    staff: List<Staff>,
    isLongClickEnable: Boolean,
    onTgClick: (String) -> Unit,
    onLongClick: (Staff) -> Unit,
) {
    LazyColumn {
        items(
            items = staff,
            key = { it.name }
        ) { item ->
            StaffItem(
                staffItem = item,
                onTgClick = { onTgClick(it) },
                isLongClickEnable = isLongClickEnable,
                onLongClick = onLongClick
            )
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun StaffItem(
    staffItem: Staff,
    isLongClickEnable: Boolean,
    onLongClick: (Staff) -> Unit,
    onTgClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = isLongClickEnable,
                onLongClick = {
                    onLongClick(staffItem)
                },
                onClick = {},
            )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Green,
                    shape = CircleShape
                ),
            model = staffItem.photoName,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                PlaceholderImage()
            },
            error = {
                PlaceholderImage()
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = staffItem.name,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onTgClick(staffItem.telegramLink)
                        },
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
private fun StaffItemPreview() {
    AYLTheme {
        StaffItem(
            staffItem = Staff(
                name = "Иван Иванов",
                position = "Старший разработчик",
                bio = "Опыт 10 лет, эксперт в Compose",
                photoName = "ivan.jpg",
                telegramLink = "https://t.me/ivan"
            ),
            onTgClick = {},
            isLongClickEnable = true,
            onLongClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StaffListScreenPreview() {
    AYLTheme {
        StaffListScreen(
            onAction = {},
            isLongClickEnable = true,
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

@Preview(showBackground = true)
@Composable
private fun StaffListScreenErrorPreview() {
    AYLTheme {
        StaffListScreen(
            onAction = {},
            isLongClickEnable = true,
            state = StaffListState(
                isLoading = false,
                staff = null,
                error = AppError.Network
            )
        )
    }
}