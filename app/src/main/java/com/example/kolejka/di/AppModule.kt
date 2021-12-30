package com.example.kolejka.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.kolejka.data.util.Constants.JWT_TOKEN
import com.example.kolejka.data.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideJwtToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString(JWT_TOKEN, "") ?: ""
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val modifiedRequest = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                it.proceed(modifiedRequest)
            }
            .build()
    }
}