package com.example.kolejka.view.ui.screens.password_change.enter_email

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EnterEmailScreen(
    navController: NavController,
    viewModel: EnterEmailViewModel = hiltViewModel()
) {

    val emailState = viewModel.emailState.value
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
                    text = "Enter your email",
                    style = MaterialTheme.typography.h1,
                    color = DarkPurple
                )

                Spacer(modifier = Modifier.size(Space16))

                StandardTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailState.text,
                    hint = stringResource(R.string.email),
                    onTextChanged = {viewModel.onEvent(EnterEmailEvent.EnteredEmail(it))},
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.size(Space16))

                if (!loading) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp),
                        shape = CircleShape,
                        onClick = {
                            viewModel.onEvent(EnterEmailEvent.CheckEmail)
                        })
                    {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.check_email_icon)
                            )
                            Spacer(modifier = Modifier.size(Space4))
                            Text(text = stringResource(R.string.send_verification_code))
                        }
                    }
                } else{
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = DarkPurple
                    )
                }
            }
        }
    }
}