package com.example.kolejka.data.features.post.repository

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.requests.CreatePostRequest
import com.example.kolejka.data.features.post.paging.PostSource
import com.example.kolejka.data.util.Constants.POSTS_PAGE_SIZE
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    suspend fun createPost(
        title: String,
        description: String,
        limit: Int?,
        type: Int,
        imageUri: Uri
    ): SimpleResource

}