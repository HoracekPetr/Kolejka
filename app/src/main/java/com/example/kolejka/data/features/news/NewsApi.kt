package com.example.kolejka.data.features.news

import com.example.kolejka.data.features.news.dto.NewsDto
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import com.example.kolejka.models.News
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("/api/news/get")
    suspend fun getNews(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE
    ): BasicApiResponse<List<NewsDto>>


    @GET("/api/news/get/{id}")
    suspend fun getNewsById(
        @Path("id" ) newsId: String
    ): BasicApiResponse<NewsDto>
}