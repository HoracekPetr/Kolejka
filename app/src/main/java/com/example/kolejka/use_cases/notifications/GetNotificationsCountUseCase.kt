package com.example.kolejka.use_cases.notifications

import com.example.kolejka.data.features.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetNotificationsCountUseCase(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(): Flow<Int> = notificationRepository.getNotificationsCount()
        .catch { emit(0) }
}