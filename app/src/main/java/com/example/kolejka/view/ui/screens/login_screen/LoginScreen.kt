package com.example.kolejka.view.ui.screens.login_screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.example.kolejka.view.theme.Space12
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.ui.screens.register_screen.UiEvent
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.errors.Errors
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = hiltViewModel(), navController: NavController) {

    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val loading = viewModel.isLoading.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginScreenViewModel.UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext),
                        duration = SnackbarDuration.Long
                    )
                }
                is LoginScreenViewModel.UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
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
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.h1,
                    color = DarkPurple
                )

                Spacer(modifier = Modifier.size(Space16))

                //EMAIL FIELD

                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailState.text,
                    hint = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    error = when (emailState.error) {
                        Errors.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    textStyle = MaterialTheme.typography.h2,
                    onTextChanged = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                    placeholderTextColor = DarkGray,
                    placeholderTextStyle = MaterialTheme.typography.h2
                )

                Spacer(modifier = Modifier.size(Space16))

                //PASSWORD FIELD

                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = passwordState.text,
                    hint = stringResource(R.string.password),
                    onTextChanged = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                    error = when (passwordState.error) {
                        is Errors.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                        else -> ""
                    },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onEvent(LoginEvent.ChangePasswordVisibility(!passwordState.visible)) }) {
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
                        viewModel.onEvent(
                            LoginEvent.Login
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(text = stringResource(R.string.login), style = MaterialTheme.typography.h3)
                }

                if (loading) {
                    Spacer(Modifier.size(Space8))
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = DarkPurple
                    )
                }

            }
            Row(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(R.string.no_account),
                    style = MaterialTheme.typography.subtitle1,
                    color = BlackAccent
                )

                Spacer(modifier = Modifier.size(Space12))

                Text(
                    text = stringResource(R.string.sign_up),
                    style = Typography.subtitle2,
                    color = DarkPurple,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            Screen.RegisterScreen.route
                        )
                    }
                )
            }
        }
    }
}