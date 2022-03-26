package com.example.kolejka.data.features.notification.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.features.notification.paging.NotificationsSource
import com.example.kolejka.data.features.post.paging.AllPostsSource
import com.example.kolejka.data.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationRepositoryImpl(
    private val api: NotificationApi
): NotificationRepository {

    override val notifications: Flow<PagingData<NotificationDto>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            NotificationsSource(api)
        }.flow

    override fun getNotificationsCount(): Flow<Int> {
        return flow {
            while (true){

                emit(api.getNotificationsCount())
            }
        }
    }

    override suspend fun setNotificationsToZero() {
        return api.setNotificationsToZero()
    }
}