package com.example.kolejka.view.ui.screens.password_change.enter_verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.nedim.maildroidx.sendEmail
import com.example.kolejka.R
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.ui.components.StandardTextField
import com.example.kolejka.view.util.UiEvent
import com.example.kolejka.view.util.uitext.asString
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EnterVerificationScreen(
    viewModel: EnterVerificationViewModel = hiltViewModel(),
    navController: NavController,
    userId: String? = null,
    code: String? = null
) {

    val codeState = viewModel.codeState.value
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
                    text = stringResource(R.string.enter_verification_code),
                    style = MaterialTheme.typography.h6,
                    color = DarkPurple
                )

                Spacer(modifier = Modifier.size(Space16))

                StandardTextField(
                    modifier = Modifier.width(150.dp),
                    text = codeState.text,
                    hint = stringResource(R.string.code),
                    onTextChanged = { viewModel.onEvent(EnterVerificationEvent.EnteredCode(it)) },
                    placeholderTextColor = DarkGray,
                    textStyle = MaterialTheme.typography.h2,
                    placeholderTextStyle = MaterialTheme.typography.h2
                )

                Spacer(modifier = Modifier.size(Space16))

                if (!loading) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp),
                        shape = CircleShape,
                        onClick = { viewModel.onEvent(EnterVerificationEvent.CheckCode) })
                    {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = "Image")
                            Spacer(modifier = Modifier.size(Space4))
                            Text(text = "Check verification code")
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