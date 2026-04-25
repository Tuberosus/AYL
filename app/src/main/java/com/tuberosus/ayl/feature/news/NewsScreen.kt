package com.tuberosus.ayl.feature.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuberosus.ayl.feature.gallery.GalleryViewModel
import com.tuberosus.ayl.ui.theme.AYLTheme

@Composable
fun NewsScreenRoot() {
    NewsScreen()
}

@Composable
private fun NewsScreen(
    viewModel: GalleryViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NewsScreen"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsScreenPreview() {
    AYLTheme {
        NewsScreenRoot()
    }
}