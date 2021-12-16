package com.example.kolejka.view.ui.screens.register_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.errors.AuthError

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val localFocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { localFocusManager.clearFocus() }
            .padding(
                start = PaddingMedium,
                end = PaddingMedium,
                top = PaddingMedium,
                bottom = PaddingExtraLarge
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        {
            Text(
                text = stringResource(R.string.register),
                style = MaterialTheme.typography.h1,
                color = DarkPurple
            )

            Spacer(modifier = Modifier.size(Space16))

            //EMAIL FIELD

            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = emailState.text,
                hint = stringResource(R.string.email),
                onTextChanged = {
                    viewModel.onEvent(
                        RegisterEvent.EnteredEmail(it)
                    )
                },
                error = when (emailState.error) {
                    is AuthError.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                    is AuthError.InvalidEmail -> stringResource(id = R.string.invalid_email)
                    else -> ""
                },
                placeholderTextColor = DarkGray,
                textStyle = MaterialTheme.typography.h2,
                placeholderTextStyle = MaterialTheme.typography.h2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            Spacer(modifier = Modifier.size(Space16))

            //USERNAME FIELD

            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = usernameState.text,
                hint = stringResource(R.string.username),
                textStyle = MaterialTheme.typography.h2,
                onTextChanged = {
                    viewModel.onEvent(
                        RegisterEvent.EnteredUsername(it)
                    )
                },
                error = when (usernameState.error) {
                    is AuthError.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                    is AuthError.InputTooShort -> stringResource(id = R.string.username_too_short, Constants.MIN_USERNAME_LENGTH)
                    else -> ""
                },
                placeholderTextColor = DarkGray,
                placeholderTextStyle = MaterialTheme.typography.h2
            )

            Spacer(modifier = Modifier.size(Space16))

            //PASSWORD FIELD

            StandardTextField(
                modifier = Modifier.fillMaxWidth(),
                text = passwordState.text,
                hint = stringResource(R.string.password),
                onTextChanged = {
                    viewModel.onEvent(
                        RegisterEvent.EnteredPassword(it)
                    )
                },
                error = when (passwordState.error) {
                    is AuthError.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                    is AuthError.InputTooShort -> stringResource(id = R.string.password_too_short, Constants.MIN_PASSWORD_LENGTH)
                    is AuthError.InvalidPassword -> stringResource(id = R.string.invalid_password)
                    else -> ""
                },
                placeholderTextColor = DarkGray,
                textStyle = MaterialTheme.typography.h2,
                placeholderTextStyle = MaterialTheme.typography.h2,
                trailingIcon = {
                    IconButton(onClick = {viewModel.onEvent(RegisterEvent.ChangePasswordVisibility(!passwordState.visible))}) {
                        Icon(
                            imageVector = if (passwordState.visible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(R.string.change_pwd_visibility)
                        )
                    }
                },
                visualTransformation = if (passwordState.visible) VisualTransformation.None else PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(Space16))

            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            ) {
                Text(text = stringResource(R.string.register), style = MaterialTheme.typography.h3)
            }

        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                style = MaterialTheme.typography.subtitle1,
                color = BlackAccent
            )

            Spacer(modifier = Modifier.size(Space12))

            Text(
                text = stringResource(R.string.sign_in),
                style = Typography.subtitle2,
                color = DarkPurple,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                    navController.navigate(
                        Screen.LoginScreen.route
                    )
                })
        }
    }
}