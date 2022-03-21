package com.example.kolejka.use_cases.notifications

import com.example.kolejka.data.features.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsCountUseCase(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(): Flow<Int> = notificationRepository.getNotificationsCount()
}