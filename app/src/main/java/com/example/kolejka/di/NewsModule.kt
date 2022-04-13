package com.example.kolejka.di

import com.example.kolejka.data.features.news.NewsApi
import com.example.kolejka.data.features.news.repository.NewsRepository
import com.example.kolejka.data.features.news.repository.NewsRepositoryImpl
import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.news.GetNewsByIdUseCase
import com.example.kolejka.use_cases.news.GetNewsUseCase
import com.example.kolejka.use_cases.user.GetUserIdUseCase
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
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsApi(client: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository{
        return NewsRepositoryImpl(newsApi = api)
    }

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repository: NewsRepository): GetNewsUseCase{
        return GetNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNewsByIdUseCase(repository: NewsRepository): GetNewsByIdUseCase{
        return GetNewsByIdUseCase(repository)
    }

}