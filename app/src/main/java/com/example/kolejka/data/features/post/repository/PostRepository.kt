package com.example.kolejka.data.features.post.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kolejka.data.features.post.paging.PostSource
import com.example.kolejka.data.util.Constants.POSTS_PAGE_SIZE
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    val posts: Flow<PagingData<Post>>

}