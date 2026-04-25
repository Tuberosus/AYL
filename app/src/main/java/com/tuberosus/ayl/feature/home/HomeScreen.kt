package com.tuberosus.ayl.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tuberosus.ayl.feature.gallery.GalleryViewModel
import com.tuberosus.ayl.ui.theme.AYLTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot() {
    HomeScreen()
}

@Composable
private fun HomeScreen(
    viewModel: GalleryViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HomeScreen"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AYLTheme {
        HomeScreenRoot()
    }
}