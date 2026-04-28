package com.tuberosus.ayl.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R

@Composable
fun ColumnScope.AylLogo(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = Modifier
            .padding(24.dp)
            .size(120.dp)
            .align(alignment = Alignment.CenterHorizontally),
        painter = painterResource(R.drawable.logo),
        contentDescription = null,
    )
}