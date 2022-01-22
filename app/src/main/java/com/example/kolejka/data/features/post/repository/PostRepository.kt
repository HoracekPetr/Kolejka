package com.example.kolejka.data.features.post.repository

import android.net.Uri
import androidx.paging.PagingData
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    val postsByCreator: Flow<PagingData<Post>>

    val postsWhereMember: Flow<PagingData<Post>>

    suspend fun getPostById(postId: String): Resource<Post>

    suspend fun createPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri
    ): SimpleResource

}