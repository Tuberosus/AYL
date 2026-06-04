package com.tuberosus.ayl.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R

@Composable
fun ColumnScope.AylLogo(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    isLoginEnabled: Boolean = false,
) {
    var clickCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(clickCount) {
        if (clickCount > 4 && isLoginEnabled) {
            onLoginClick()
        }
    }

    Image(
        modifier = modifier
            .padding(24.dp)
            .size(120.dp)
            .align(alignment = Alignment.CenterHorizontally)
            .clickable(
                onClick = { clickCount++ },
                enabled = isLoginEnabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
    )
}