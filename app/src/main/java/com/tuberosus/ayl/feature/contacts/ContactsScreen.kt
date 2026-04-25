package com.tuberosus.ayl.feature.contacts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tuberosus.ayl.ui.theme.AYLTheme

@Composable
fun ContactsScreenRoot() {
    ContactsScreen()
}

@Composable
private fun ContactsScreen(
    viewModel: ContactsViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "ContactsScreen"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactsScreenPreview() {
    AYLTheme {
        ContactsScreenRoot()
    }
}