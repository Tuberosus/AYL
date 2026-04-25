package com.tuberosus.ayl.feature.home.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tuberosus.ayl.feature.home.about.AboutViewModel
import com.tuberosus.ayl.ui.theme.AYLTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreenRoot() {
    AboutScreen()
}

@Composable
private fun AboutScreen(
    viewModel: AboutViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "AboutScreen"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    AYLTheme {
        AboutScreenRoot()
    }
}