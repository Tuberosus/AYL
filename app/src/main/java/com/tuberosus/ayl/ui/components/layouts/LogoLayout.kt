package com.tuberosus.ayl.ui.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.ui.components.AylLogo
import com.tuberosus.ayl.ui.components.TitleWithUnderline
import com.tuberosus.ayl.ui.theme.AYLTheme

@Composable
fun LogoLayout(
    modifier: Modifier = Modifier,
    title: String,
    onLoginClick: () -> Unit = {},
    isLoginEnabled: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {
        AylLogo(
            onLoginClick = onLoginClick,
            isLoginEnabled = isLoginEnabled,
        )
        TitleWithUnderline(title)
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoLayoutPreview() {
    AYLTheme {
        LogoLayout(
            title = "О нас"
        ) {
            Text(
                text = "Hello World!"
            )
        }
    }
}

@Preview
@Composable
private fun LogoLayoutDarkPreview() {
    AYLTheme(darkTheme = true) {
        LogoLayout(
            title = "О нас"
        ) {
            Text(
                text = "Hello World!"
            )
        }
    }
}