package com.example.kolejka.view.ui.screens.post_detail_screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kolejka.R

@Composable
fun DeletePostDialog(
    showDialog: Boolean = false,
    onDismissRequestClick: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequestClick() },
        title = {
            Text(text = stringResource(R.string.delete_post), style = MaterialTheme.typography.body1)
        },
        text = {
            Text(text = stringResource(R.string.delete_post_really))
        },
        confirmButton = {
            Button(onClick = {onConfirmButtonClick()}) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "YES, DELETE")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequestClick() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "NO, CLOSE DIALOG"
                )
            }
        }
    )
}