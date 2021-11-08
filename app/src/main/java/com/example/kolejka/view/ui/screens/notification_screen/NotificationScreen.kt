package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NotificationScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "Notifications Screen", modifier = Modifier.align(Alignment.Center))
    }
}