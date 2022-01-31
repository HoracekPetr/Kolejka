package com.example.kolejka.models.notification

data class Notification(
        val parentId: String,
        val username: String,
        val notificationType: NotificationType,
        val formattedTime: String,
)
