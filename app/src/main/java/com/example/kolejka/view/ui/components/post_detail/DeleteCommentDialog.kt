package com.example.kolejka.view.ui.components.post_detail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kolejka.R

@Composable
fun DeleteCommentDialog(
    onDismissRequestClick: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequestClick() },
        title = {
            Text(text = stringResource(R.string.delete_comment), style = MaterialTheme.typography.body1)
        },
        text = {
            Text(text = stringResource(R.string.delete_comment_really))
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