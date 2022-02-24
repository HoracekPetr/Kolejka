package com.example.kolejka.view.ui.components.profile

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kolejka.R

@Composable
fun LogoutDialog(
    onLogoutConfirmClick: () -> Unit = {},
    onLogoutDialogDismiss: () -> Unit = {}
) {

    AlertDialog(
        onDismissRequest = { onLogoutDialogDismiss() },
        title = {
            Text(text = stringResource(R.string.log_out), style = MaterialTheme.typography.body1)
        },
        text = {
            Text(text = stringResource(R.string.log_out_question))
        },
        confirmButton = {
            Button(onClick = {onLogoutConfirmClick()}) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "YES, LOG OUT")
            }
        },
        dismissButton = {
            Button(onClick = { onLogoutDialogDismiss() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "NO, CLOSE DIALOG"
                )
            }
        }
    )

}