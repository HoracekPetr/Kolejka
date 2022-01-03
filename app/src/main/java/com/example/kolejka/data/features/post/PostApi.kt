package com.example.kolejka.data.features.post

import com.example.kolejka.models.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/api/post/getPostsByAll")
    suspend fun getPostsByAll(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<Post>
}