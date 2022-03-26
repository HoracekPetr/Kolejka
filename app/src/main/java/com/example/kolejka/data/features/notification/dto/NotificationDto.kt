package com.example.kolejka.data.features.notification.dto

import com.example.kolejka.models.notification.Notification
import com.example.kolejka.models.notification.NotificationType
import java.text.SimpleDateFormat
import java.util.*

data class NotificationDto(
    val timestamp: Long,
    val parentId: String,
    val type: Int,
    val username: String,
    val id: String
) {
    fun toNotification(): Notification {
        return Notification(
            parentId = parentId,
            username = username,
            notificationType = when(type){
                NotificationType.JoinedEvent.type -> NotificationType.JoinedEvent
                NotificationType.WantsOffer.type -> NotificationType.WantsOffer
                NotificationType.CommentedOn.type -> NotificationType.CommentedOn
                else -> NotificationType.JoinedEvent
            },
            formattedTime = SimpleDateFormat("MMM dd, HH:mm ", Locale.getDefault()).run {
                format(timestamp)
            }
        )
    }
}
