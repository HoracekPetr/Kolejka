package com.example.kolejka.data.features.notification.repository

import androidx.paging.PagingData
import com.example.kolejka.data.features.notification.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    val notifications: Flow<PagingData<NotificationDto>>
}