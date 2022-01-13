package com.example.kolejka.use_cases.post

import androidx.paging.PagingData
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow

class GetPostsWhereMemberUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return postRepository.postsWhereMember
    }
}