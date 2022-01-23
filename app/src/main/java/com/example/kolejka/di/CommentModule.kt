package com.example.kolejka.di

import com.example.kolejka.data.features.comment.CommentApi
import com.example.kolejka.data.features.comment.repository.CommentRepository
import com.example.kolejka.data.features.comment.repository.CommentRepositoryImpl
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.util.Constants
import com.example.kolejka.use_cases.comment.GetCommentsForPostUseCase
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
object CommentModule {

    @Provides
    @Singleton
    fun provideCommentApi(client: OkHttpClient): CommentApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(commentApi: CommentApi): CommentRepository{
        return CommentRepositoryImpl(commentApi)
    }

    @Provides
    @Singleton
    fun provideGetCommentsForPostUseCase(repository: CommentRepository): GetCommentsForPostUseCase{
        return GetCommentsForPostUseCase(repository)
    }
}