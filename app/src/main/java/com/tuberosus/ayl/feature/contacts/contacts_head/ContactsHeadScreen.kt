package com.tuberosus.ayl.feature.contacts.contacts_head

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tuberosus.ayl.ui.theme.AYLTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactsHeadScreenRoot() {
    ContactsHeadScreen()
}

@Composable
private fun ContactsHeadScreen(
    viewModel: ContactsHeadViewModel = koinViewModel()
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
private fun ContactsHeadScreenPreview() {
    AYLTheme {
        ContactsHeadScreenRoot()
    }
}