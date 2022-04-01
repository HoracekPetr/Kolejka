package com.example.kolejka.di

import android.app.Notification
import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.features.notification.repository.NotificationRepository
import com.example.kolejka.data.features.notification.repository.NotificationRepositoryImpl
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.features.post.repository.PostRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.notifications.DeleteNotificationsForUserUseCase
import com.example.kolejka.use_cases.notifications.GetNotificationsCountUseCase
import com.example.kolejka.use_cases.notifications.GetNotificationsUseCase
import com.example.kolejka.use_cases.notifications.SetNotificationsToZeroUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationApi(client: OkHttpClient): NotificationApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationApi::class.java)

    }

    @Provides
    @Singleton
    fun provideNotificationRepository(notificationApi: NotificationApi): NotificationRepository {
        return NotificationRepositoryImpl(notificationApi)
    }

    @Provides
    @Singleton
    fun provideGetNotificationUseCase(repository: NotificationRepository): GetNotificationsUseCase{
        return GetNotificationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNotificationsCountUseCase(repository: NotificationRepository): GetNotificationsCountUseCase{
        return GetNotificationsCountUseCase((repository))
    }

    @Provides
    @Singleton
    fun setNotificationToZeroUseCase(repository: NotificationRepository): SetNotificationsToZeroUseCase {
        return SetNotificationsToZeroUseCase(repository)
    }

    @Provides
    @Singleton
    fun deleteNotificationsForUserUseCase(repository: NotificationRepository): DeleteNotificationsForUserUseCase {
        return DeleteNotificationsForUserUseCase(repository)
    }
}