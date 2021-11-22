package com.example.kolejka.view.ui.components.bottom_navigation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.kolejka.view.theme.MediumOpaquePurple
import com.example.kolejka.view.theme.Space8

@Composable
fun FloatingAddPostButton(
    showButton: Boolean,
    buttonIcon: ImageVector,
    buttonText: String,
    iconDescription: String,
    onButtonClick: () -> Unit = {}
) {
    if(showButton) {
        ExtendedFloatingActionButton(
            text = {
                Text(text = buttonText, style = MaterialTheme.typography.subtitle2)
            },
            onClick = {onButtonClick()},
            icon = {
                Icon(imageVector = buttonIcon, contentDescription = iconDescription)
            },
            backgroundColor = MediumOpaquePurple,
            elevation = FloatingActionButtonDefaults.elevation(Space8)
        )
    }
}