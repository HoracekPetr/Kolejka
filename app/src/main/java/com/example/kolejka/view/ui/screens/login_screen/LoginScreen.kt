package com.example.kolejka.view.ui.screens.login_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.theme.Space12
import com.example.kolejka.view.ui.components.PasswordTextField
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.util.Screen


@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = hiltViewModel(), navController: NavController) {

    val localFocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = PaddingMedium,
                end = PaddingMedium,
                top = PaddingMedium,
                bottom = PaddingLarge
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { localFocusManager.clearFocus() },
/*            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center*/
        )
        {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.h1,
                color = DarkPurple
            )

            Spacer(modifier = Modifier.size(Space16))

            StandardTextField(
                text = viewModel.usernameText.value,
                hint = stringResource(R.string.username),
                onTextChanged = { viewModel.setUsernameText(it) })

            Spacer(modifier = Modifier.size(Space16))

            PasswordTextField(
                text = viewModel.passwordText.value,
                hint = stringResource(R.string.password),
                onTextChanged = { viewModel.setPasswordText(it) },
                isPasswordVisible = viewModel.passwordVisibility.value
            ) {
                viewModel.setPasswordVisibility()
            }

            Spacer(modifier = Modifier.size(Space16))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            ) {
                Text(text = stringResource(R.string.login), style = MaterialTheme.typography.h3)
            }

        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = stringResource(R.string.no_account), style = MaterialTheme.typography.subtitle1, color = BlackAccent)

            Spacer(modifier = Modifier.size(Space12))

            Text(text = stringResource(R.string.sign_up), style = Typography.subtitle2, color = DarkPurple, modifier = Modifier.clickable {
                navController.navigate(
                    Screen.RegisterScreen.route
                )
            })
        }
    }
}