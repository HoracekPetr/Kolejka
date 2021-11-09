package com.example.kolejka.view.ui.components.bottom_navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kolejka.view.theme.MediumOpaquePurple
import com.example.kolejka.view.theme.Space8

@Composable
fun FloatingAddPostButton(
    showButton: Boolean,
    buttonText: String,
    iconDescription: String,
) {
    if(showButton) {
        ExtendedFloatingActionButton(
            text = {
                Text(text = buttonText, style = MaterialTheme.typography.subtitle2)
            },
            onClick = { /*TODO*/ },
            icon = {
                Icon(imageVector = Icons.Filled.PostAdd, contentDescription = iconDescription)
            },
            backgroundColor = MediumOpaquePurple,
            elevation = FloatingActionButtonDefaults.elevation(Space8)
        )
    }
}