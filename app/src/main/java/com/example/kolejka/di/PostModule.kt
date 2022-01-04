package com.example.kolejka.di

import android.content.Context
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.features.post.repository.PostRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.post.CreatePostUseCase
import com.example.kolejka.use_cases.post.GetAllPostsUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)

    }

    @Provides
    @Singleton
    fun providePostRepository(postApi: PostApi, gson: Gson, @ApplicationContext appContext: Context): PostRepository {
        return PostRepositoryImpl(postApi, gson, appContext)
    }

    @Provides
    @Singleton
    fun provideGetAllPostsUseCase(repository: PostRepository): GetAllPostsUseCase {
        return GetAllPostsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreatePostsUseCase(repository: PostRepository): CreatePostUseCase {
        return CreatePostUseCase(repository)
    }
}