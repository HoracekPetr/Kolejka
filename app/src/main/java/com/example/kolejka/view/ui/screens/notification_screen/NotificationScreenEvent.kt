package com.example.kolejka.view.ui.screens.notification_screen

import androidx.paging.compose.LazyPagingItems
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.models.notification.Notification

sealed class NotificationScreenEvent{
    object ExpandDeleteNotification: NotificationScreenEvent()
    data class DeleteNotification(val notifications: LazyPagingItems<NotificationDto>): NotificationScreenEvent()
}
