package com.example.kolejka.di

import android.content.SharedPreferences
import com.example.kolejka.data.features.auth.AuthApi
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.features.auth.repository.AuthRepositoryImpl
import com.example.kolejka.data.util.Constants.BASE_URL
import com.example.kolejka.use_cases.LoginUseCase
import com.example.kolejka.use_cases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)

    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi, sharedPreferences: SharedPreferences): AuthRepository{
        return AuthRepositoryImpl(authApi, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase{
        return LoginUseCase(repository)
    }
}