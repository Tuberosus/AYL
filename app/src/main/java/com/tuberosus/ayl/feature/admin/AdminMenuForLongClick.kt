package com.tuberosus.ayl.feature.admin

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.theme.AYLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMenuBottomSheet(
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: (() -> Unit)? = null,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        AdminMenuForLongClick(
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
private fun AdminMenuForLongClick(
    onEditClick: (() -> Unit)?,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(
                top = 8.dp,
                bottom = 24.dp
            )
    ) {
        onEditClick?.let { onClick ->
            MenuItem(
                leadingIcon = R.drawable.ic_edit,
                title = "Редактировать",
                onClick = onClick,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        MenuItem(
            leadingIcon = R.drawable.ic_delete,
            title = "Удалить",
            onClick = onDeleteClick,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun MenuItem(
    @DrawableRes leadingIcon: Int,
    title: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(leadingIcon),
            contentDescription = null,
            tint = color
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminMenuBottomSheetPreview() {
    AYLTheme {
        AdminMenuBottomSheet(
            onDismiss = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}