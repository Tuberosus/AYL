package com.tuberosus.ayl.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R

@Composable
fun PlaceholderImage(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .padding(12.dp),
        painter = painterResource(R.drawable.logo_tight_optimized),
        contentDescription = null
    )
}