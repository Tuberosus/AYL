package com.tuberosus.ayl.ui.components.layouts

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.components.topbars.BackTopAppBar
import com.tuberosus.ayl.ui.theme.AYLTheme

@Composable
fun TopBarWithBackLayout(
    onBackClick: () -> Unit,
    title: String,
    scrollState: ScrollState = rememberScrollState(),
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        BackTopAppBar(
            onBackClick = onBackClick
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            TitleWithUnderline(title)
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarWithBackLayoutPreview() {
    AYLTheme {
        TopBarWithBackLayout(
            title = "Contacts",
            onBackClick = {}
        ) {}
    }
}