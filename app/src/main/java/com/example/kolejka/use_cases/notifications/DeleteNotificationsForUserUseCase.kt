package com.example.kolejka.use_cases.notifications

import com.example.kolejka.data.features.notification.repository.NotificationRepository
import com.example.kolejka.data.util.SimpleResource

class DeleteNotificationsForUserUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(): SimpleResource{
        return repository.deleteNotificationsForUser()
    }
}