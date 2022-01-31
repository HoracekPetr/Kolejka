package com.example.kolejka.use_cases.notifications

import androidx.paging.PagingData
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.features.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(): Flow<PagingData<NotificationDto>>{
        return notificationRepository.notifications
    }
}