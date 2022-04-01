package com.example.kolejka.data.features.notification.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kolejka.R
import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.features.notification.paging.NotificationsSource
import com.example.kolejka.data.features.post.paging.AllPostsSource
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.view.util.uitext.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

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

    override suspend fun deleteNotificationsForUser(): SimpleResource {
        return try{
            val request = api.deleteNotificationsForUser()

            if(request.successful){
                Resource.Success(Unit)
            } else {
                request.message?.let { msg ->
                    Resource.Error(uiText = UiText.StringDynamic(msg))
                } ?: Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }
        } catch(e: IOException){
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch(e: HttpException){
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }
}