package com.tuberosus.ayl.feature.home.documents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.ui.components.AylWebView
import com.tuberosus.ayl.ui.components.BackTopAppBar
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun DocumentsScreenRoot(
    onBackClick: () -> Unit,
    viewModel: DocumentsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is DocumentsEvent.OnBackClick -> onBackClick()
        }
    }

    DocumentsScreen(
        state = state,
        onBackClick = {
            viewModel.onAction(DocumentsAction.OnBackClick)
        }
    )
}

@Composable
private fun DocumentsScreen(
    state: DocumentsState,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackTopAppBar(
            onBackClick = onBackClick,
        )
        AylWebView(
            url = state.url,
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentsScreenPreview() {
    AYLTheme {
        DocumentsScreen(
            state = DocumentsState(),
            onBackClick = {},
        )
    }
}