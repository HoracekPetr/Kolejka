package com.example.kolejka.use_cases.notifications

import com.example.kolejka.data.features.notification.repository.NotificationRepository

class SetNotificationsToZeroUseCase(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke(){
        return repository.setNotificationsToZero()
    }
}