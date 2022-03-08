package com.example.kolejka.view.ui.screens.register_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
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
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.util.Constants
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.errors.Errors
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val scaffoldState = rememberScaffoldState()

    val viewRequester = BringIntoViewRequester()

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val usernameState = viewModel.usernameState.value
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val verificationState = viewModel.verificationCodeState.value
    val registerState = viewModel.registerState.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(localContext),
                        duration = SnackbarDuration.Short
                    )
                }
                else -> {}
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
                    bottom = PaddingLarge
                )
                .verticalScroll(scrollState)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    delay(200)
                                    viewRequester.bringIntoView()
                                }
                            }
                        }
                        .bringIntoViewRequester(viewRequester),
                    text = emailState.text,
                    hint = stringResource(R.string.email),
                    onTextChanged = {
                        viewModel.onEvent(
                            RegisterEvent.EnteredEmail(it)
                        )
                    },
                    error = when (emailState.error) {
                        is Errors.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                        is Errors.InvalidEmail -> stringResource(id = R.string.invalid_email)
                        is Errors.NotTULEmail -> stringResource(R.string.tulmailerror)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    delay(200)
                                    viewRequester.bringIntoView()
                                }
                            }
                        }
                        .bringIntoViewRequester(viewRequester),
                    text = usernameState.text,
                    hint = stringResource(R.string.username),
                    textStyle = MaterialTheme.typography.h2,
                    onTextChanged = {
                        viewModel.onEvent(
                            RegisterEvent.EnteredUsername(it)
                        )
                    },
                    error = when (usernameState.error) {
                        is Errors.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                        is Errors.InputTooShort -> stringResource(
                            id = R.string.username_too_short,
                            Constants.MIN_USERNAME_LENGTH
                        )
                        else -> ""
                    },
                    placeholderTextColor = DarkGray,
                    placeholderTextStyle = MaterialTheme.typography.h2
                )

                Spacer(modifier = Modifier.size(Space16))

                //PASSWORD FIELD

                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusEvent {
                            if (it.isFocused) {
                                coroutineScope.launch {
                                    delay(200)
                                    viewRequester.bringIntoView()
                                }
                            }
                        }
                        .bringIntoViewRequester(viewRequester),
                    text = passwordState.text,
                    hint = stringResource(R.string.password),
                    onTextChanged = {
                        viewModel.onEvent(
                            RegisterEvent.EnteredPassword(it)
                        )
                    },
                    error = when (passwordState.error) {
                        is Errors.EmptyField -> stringResource(id = R.string.this_field_cant_be_empty)
                        is Errors.InputTooShort -> stringResource(
                            id = R.string.password_too_short,
                            Constants.MIN_PASSWORD_LENGTH
                        )
                        is Errors.InvalidPassword -> stringResource(id = R.string.invalid_password)
                        else -> ""
                    },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2,
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.onEvent(
                                RegisterEvent.ChangePasswordVisibility(
                                    !passwordState.visible
                                )
                            )
                        }) {
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

                Spacer(modifier = Modifier.size(Space36))

                //Verification

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StandardTextField(
                        modifier = Modifier
                            .width(250.dp)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    coroutineScope.launch {
                                        delay(200)
                                        viewRequester.bringIntoView()
                                    }
                                }
                            }
                            .bringIntoViewRequester(viewRequester),
                        text = verificationState.text,
                        hint = stringResource(R.string.verification_code),
                        onTextChanged = { viewModel.onEvent(RegisterEvent.EnteredVerificationCode(it)) },
                        placeholderTextColor = DarkGray,
                        textStyle = MaterialTheme.typography.h3,
                        error = when (verificationState.error) {
                            is Errors.EmptyField -> stringResource(id = R.string.fields_blank)
                            else -> ""
                        },
                        placeholderTextStyle = MaterialTheme.typography.h3,
                        errorBelow = true
                    )
                    Spacer(modifier = Modifier.size(Space8))
                    if (registerState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(40.dp, 30.dp))
                    } else {
                        Button(
                            modifier = Modifier.width(70.dp),
                            onClick = { viewModel.onEvent(RegisterEvent.SendVerificationCode) },
                            shape = CircleShape,
                            elevation = ButtonDefaults.elevation(Space4)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send verification code."
                                )
                                Text(
                                    text = stringResource(id = R.string.send),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.size(Space36))

                if (registerState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(CenterHorizontally),
                        color = DarkPurple
                    )
                } else {
                    Button(
                        onClick = {
                            viewModel.onEvent(RegisterEvent.Register)
                        },
                        enabled = !registerState.isLoading,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.register),
                            style = MaterialTheme.typography.h3
                        )
                    }
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
                        navController.navigate(
                            Screen.LoginScreen.route
                        ) {
                            popUpTo(Screen.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    })
            }
        }
    }
}