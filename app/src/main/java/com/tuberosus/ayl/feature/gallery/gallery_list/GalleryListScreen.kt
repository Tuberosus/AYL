package com.tuberosus.ayl.feature.gallery.gallery_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.tuberosus.ayl.R
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.ui.components.ErrorView
import com.tuberosus.ayl.ui.components.FullScreenProgressBar
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryListScreenRoot(
    viewModel: GalleryListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GalleryListScreen(state)
}

@Composable
private fun GalleryListScreen(
    state: GalleryListState
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
        TitleWithUnderline("Галерея")
        Spacer(modifier = Modifier.height(4.dp))

        when {
            state.isLoading ->
                FullScreenProgressBar()

            state.error != null ->
                ErrorView(state.error)

            state.groupedPhotos != null ->
                ImageGrid(
                    groupedPhotos = state.groupedPhotos,
                    onClick = {}
                )
        }
    }
}

@Composable
private fun ImageGrid(
    groupedPhotos: Map<String, List<GalleryPhoto>>,
    onClick: (String) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Adaptive(130.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        groupedPhotos.forEach { (title, photos) ->
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                SectionTitle(title)
            }
            items(items = photos) { photo ->
                GridPhoto(photo.imageName) { }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        modifier = Modifier
            .padding(bottom = 8.dp),
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontSize = 20.sp,
    )
}

@Composable
fun GridPhoto(
    imageUrl: String,
    onClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer()
                ),
            onSuccess = { isLoading = false },
            onError = { isLoading = false },
            placeholder = painterResource(R.drawable.ayl_logo_placeholder),
            error = painterResource(R.drawable.ayl_logo_placeholder),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryListScreenPreview() {
    AYLTheme {
        GalleryListScreen(
            state = GalleryListState()
        )
    }
}