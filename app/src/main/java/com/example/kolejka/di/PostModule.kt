package com.example.kolejka.di

import android.content.SharedPreferences
import com.example.kolejka.data.features.auth.AuthApi
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.features.auth.repository.AuthRepositoryImpl
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.data.features.post.repository.PostRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.GetAllPostsUseCase
import com.example.kolejka.use_cases.RegisterUseCase
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
    fun providePostRepository(postApi: PostApi): PostRepository {
        return PostRepositoryImpl(postApi)
    }

    @Provides
    @Singleton
    fun provideGetAllPostsUseCase(repository: PostRepository): GetAllPostsUseCase {
        return GetAllPostsUseCase(repository)
    }
}