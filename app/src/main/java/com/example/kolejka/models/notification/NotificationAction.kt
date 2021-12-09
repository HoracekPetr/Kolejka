package com.example.kolejka.models.notification

sealed class NotificationAction{
    object JoinedEvent: NotificationAction()
    object CalledDibs: NotificationAction()
    object CommentedOn: NotificationAction()
}
