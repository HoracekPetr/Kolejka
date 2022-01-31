package com.example.kolejka.di

import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.features.notification.repository.NotificationRepository
import com.example.kolejka.data.features.notification.repository.NotificationRepositoryImpl
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.features.post.repository.PostRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.notifications.GetNotificationsUseCase
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
}