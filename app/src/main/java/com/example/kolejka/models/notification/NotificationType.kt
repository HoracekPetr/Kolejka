package com.example.kolejka.models.notification

sealed class NotificationType(val type: Int) {
    object JoinedEvent : NotificationType(0)
    object WantsOffer : NotificationType(1)
    object CommentedOn: NotificationType(2)
}
