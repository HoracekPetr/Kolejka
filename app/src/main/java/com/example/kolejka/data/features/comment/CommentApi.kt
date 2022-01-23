package com.example.kolejka.data.features.comment

import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.models.Comment
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentApi {

    @GET("/api/comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): BasicApiResponse<List<Comment>>
}