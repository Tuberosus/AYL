package com.tuberosus.ayl.feature.contacts.donation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.ui.components.AylWebView
import com.tuberosus.ayl.ui.components.BackTopAppBar
import com.tuberosus.ayl.ui.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun DonationScreenRoot(
    onBackClick: () -> Unit,
    viewModel: DonationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is DonationEvent.OnBackClick -> { onBackClick() }
        }
    }

    DonationScreen(
        state = state,
        onBackClick = { viewModel.onAction(DonationAction.OnBackClick) }
    )
}

@Composable
private fun DonationScreen(
    state: DonationState,
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