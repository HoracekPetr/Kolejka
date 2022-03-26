package com.example.kolejka.view.ui.screens.password_change.change_password

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.kolejka.view.ui.screens.register_screen.RegisterEvent
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.errors.Errors
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    navController: NavController,
    userId: String? = null
) {

    val newPasswordState = viewModel.newPasswordState.value
    val newPasswordAgainState = viewModel.newPasswordAgainState.value
    val loading = viewModel.isLoading.value

    val localContext = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.uiText.asString(localContext),
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvent.ShowSnackbarAndNavigate -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.uiText.asString(localContext),
                        duration = SnackbarDuration.Short
                    )

                    navController.popBackStack()
                    navController.navigate(
                        uiEvent.route
                    ){
                        launchSingleTop = true
                        popUpTo(Screen.SplashScreen.route){
                            inclusive = true
                        }
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = PaddingMedium,
                    end = PaddingMedium,
                    top = PaddingMedium,
                    bottom = PaddingExtraLarge
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Choose a new password",
                    style = MaterialTheme.typography.h1,
                    color = DarkPurple
                )

                Spacer(modifier = Modifier.size(Space16))

                //NEW PASSWORD
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = newPasswordState.text,
                    hint = stringResource(R.string.new_password),
                    onTextChanged = {viewModel.onEvent(ChangePasswordEvent.EnteredNewPassword(it))},
                    placeholderTextColor = DarkGray,
                    error = when(newPasswordState.error){
                        is Errors.EmptyField -> {
                            stringResource(id = R.string.this_field_cant_be_empty)
                        }
                        is Errors.InputTooShort -> {
                            stringResource(id = R.string.password_too_short)
                        }
                        is Errors.InvalidPassword -> {
                            stringResource(id = R.string.invalid_password)
                        }
                        else -> ""
                    },
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(
                                ChangePasswordEvent.ChangePasswordVisibility(
                                    visibility = !newPasswordState.visible
                                )
                            )
                        }) {
                            Icon(
                                imageVector = if (newPasswordState.visible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(R.string.change_pwd_visibility)
                            )
                        }
                    },
                    visualTransformation = if (newPasswordState.visible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.size(Space16))

                //NEW PASSWORD AGAIN
                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = newPasswordAgainState.text,
                    hint = stringResource(R.string.new_password_again),
                    onTextChanged = {viewModel.onEvent(ChangePasswordEvent.EnteredNewPasswordAgain(it))},
                    placeholderTextColor = DarkGray,
                    error = when(newPasswordAgainState.error){
                        is Errors.EmptyField -> {
                            stringResource(id = R.string.this_field_cant_be_empty)
                        }
                        is Errors.InputTooShort -> {
                            stringResource(id = R.string.password_too_short)
                        }
                        is Errors.InvalidPassword -> {
                            stringResource(id = R.string.invalid_password)
                        }
                        else -> ""
                    },
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(
                                ChangePasswordEvent.ChangePasswordAgainVisibility(
                                    visibility = !newPasswordAgainState.visible
                                )
                            )
                        }) {
                            Icon(
                                imageVector = if (newPasswordAgainState.visible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff,
                                contentDescription = stringResource(R.string.change_pwd_visibility)
                            )
                        }
                    },
                    visualTransformation = if (newPasswordAgainState.visible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.size(Space16))

                if(!loading) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp),
                        shape = CircleShape,
                        onClick = {
                            viewModel.onEvent(ChangePasswordEvent.ChangePassword)
                        })
                    {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Password, contentDescription = "Image")
                            Spacer(modifier = Modifier.size(Space4))
                            Text(text = stringResource(R.string.set_new_password))
                        }
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = DarkPurple
                    )
                }
            }
        }
    }
}