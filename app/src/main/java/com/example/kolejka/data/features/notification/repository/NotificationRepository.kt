package com.example.kolejka.data.features.notification.repository

import androidx.paging.PagingData
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.util.SimpleResource
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    val notifications: Flow<PagingData<NotificationDto>>

    fun getNotificationsCount(): Flow<Int>

    suspend fun setNotificationsToZero(): Unit

    suspend fun deleteNotificationsForUser(): SimpleResource
}