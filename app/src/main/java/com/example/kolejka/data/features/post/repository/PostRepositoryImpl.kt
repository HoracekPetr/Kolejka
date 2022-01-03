package com.example.kolejka.data.features.post.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kolejka.R
import com.example.kolejka.data.features.auth.dto.request.RegisterAccountRequest
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.features.post.paging.PostSource
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Resource
import com.example.kolejka.models.Post
import com.example.kolejka.view.util.uitext.UiText
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val postApi: PostApi
): PostRepository {

    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.POSTS_PAGE_SIZE)) {
            PostSource(postApi)
        }.flow
}