package com.example.kolejka.models.notification

data class Notification(
        val username: String,
        val notificationType: NotificationAction,
        val formattedTime: String,
)
