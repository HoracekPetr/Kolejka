package com.example.kolejka.di

import com.example.kolejka.data.features.user.UserApi
import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.features.user.repository.UserRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.user.GetUserProfileUseCase
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
object UserModule {

    @Provides
    @Singleton
    fun provideUserApi(client: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(repository: UserRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(repository)
    }
}