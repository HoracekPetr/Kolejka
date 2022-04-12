package com.example.kolejka.data.features.post.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.kolejka.data.features.post.dto.response.PostDetailResponse
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    val postsByCreator: Flow<PagingData<Post>>

    suspend fun postsByOtherCreator(userId: String): Flow<PagingData<Post>>

    val postsWhereMember: Flow<PagingData<Post>>

    suspend fun getPostById(postId: String): Resource<PostDetailResponse>

    suspend fun addPostMember(postId: String): SimpleResource

    suspend fun createNewPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        date: String?,
        location: String?,
        postImageURL: String?
    ): SimpleResource

    suspend fun editPostInfo(
        postId: String,
        title: String,
        description: String,
        limit: Int?,
        date: String,
        location: String,
        postImageUrl: String?
    ): SimpleResource

    suspend fun deletePost(postId: String): SimpleResource

}