package com.example.kolejka.view.ui.screens.notification_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kolejka.models.notification.Notification
import com.example.kolejka.models.notification.NotificationAction
import com.example.kolejka.view.theme.PaddingMedium
import com.example.kolejka.view.ui.components.notification.NotificationComposable
import kotlin.random.Random


@Composable
fun NotificationScreen(
        viewModel: NotificationScreenViewModel = hiltViewModel(),
        navController: NavController) {
    LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingMedium)){
        items(20){
            NotificationComposable(notification = Notification(
                    username = "Petr Horáček",
                    notificationType = if(Random.nextInt(2) == 0){
                        NotificationAction.JoinedEvent
                    } else NotificationAction.CalledDibs,
                    formattedTime = viewModel.timestampToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm "
                    )
            ))
        }
    }
}