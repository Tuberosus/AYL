package com.tuberosus.ayl.ui.components.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.theme.AYLTheme
import com.tuberosus.ayl.ui.theme.Blue

@Composable
fun AdminLayout(
    onExitClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLogIn: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (isLogIn) {
                AdminTopBar(
                    onExitClick = onExitClick,
                    onAddClick = onAddClick
                )
            }
        }
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .padding(innerPaddings)
        ) {
            content()
        }
    }
}

@Composable
private fun AdminTopBar(
    onExitClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            elevation = ButtonDefaults.elevatedButtonElevation(),
            onClick = onExitClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = Blue,
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "Выход",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        IconButton(
            onClick = onAddClick
        ) {
            Icon(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(R.drawable.ic_add_circle),
                contentDescription = null,
                tint = Blue
            )
        }
    }
}

@Preview
@Composable
private fun AdminLayoutPreview() {
    AYLTheme {
        AdminLayout(
            onExitClick = {},
            onAddClick = {},
            isLogIn = true,
            content = {}
        )
    }
}