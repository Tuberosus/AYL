package com.tuberosus.ayl.ui.components.topbars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminTopBar(
    onExitClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 14.dp),
        title = {},
        navigationIcon = {
            Button(
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 2.dp
                ),
                onClick = onExitClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Blue,
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    text = "Выход",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        },
        actions = {
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
    )
}