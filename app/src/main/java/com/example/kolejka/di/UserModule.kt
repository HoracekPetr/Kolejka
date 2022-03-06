package com.example.kolejka.di

import android.content.Context
import com.example.kolejka.data.features.user.UserApi
import com.example.kolejka.data.features.user.repository.UserRepository
import com.example.kolejka.data.features.user.repository.UserRepositoryImpl
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.user.GetOtherUserProfileUseCase
import com.example.kolejka.use_cases.user.GetUserProfileUseCase
import com.example.kolejka.use_cases.user.UpdateProfileUseCase
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
    fun provideUserRepository(userApi: UserApi, gson: Gson, @ApplicationContext appContext: Context): UserRepository {
        return UserRepositoryImpl(userApi, gson, appContext)
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(repository: UserRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOtherUserProfileUseCase(repository: UserRepository): GetOtherUserProfileUseCase{
        return GetOtherUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(repository: UserRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository)
    }
}