package com.example.kolejka.use_cases.notifications

import com.example.kolejka.data.features.notification.repository.NotificationRepository

class GetNotificationsCountUseCase(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(): Int{
        return notificationRepository.getNotificationsCount()
    }
}