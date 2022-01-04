package com.example.kolejka.di

import android.content.SharedPreferences
import com.example.kolejka.data.features.auth.AuthApi
import com.example.kolejka.data.features.auth.repository.AuthRepository
import com.example.kolejka.data.features.auth.repository.AuthRepositoryImpl
import com.example.kolejka.data.util.Constants.BASE_URL
import com.example.kolejka.use_cases.auth.AuthenticateUseCase
import com.example.kolejka.use_cases.auth.LoginUseCase
import com.example.kolejka.use_cases.auth.RegisterUseCase
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
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
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
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticateUseCase(repository: AuthRepository): AuthenticateUseCase {
        return AuthenticateUseCase(repository)
    }
}