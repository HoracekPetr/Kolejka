package com.example.kolejka.view.ui.screens.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.kolejka.R
import androidx.compose.animation.core.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kolejka.view.util.Constants.SPLASH_DURATION
import com.example.kolejka.view.util.Screen
import com.example.kolejka.view.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashScreenViewModel = hiltViewModel()) {

    val scale = remember {
        Animatable(0f)
    }

    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    overshootInterpolator.getInterpolation(it)
                }
            )
        )

        delay(SPLASH_DURATION)
    }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                    navController.navigate(event.route)
                }
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.scale(scale.value),
            painter = painterResource(id = R.drawable.kolejka_splash),
            contentDescription = "splash logo"
        )
    }

}