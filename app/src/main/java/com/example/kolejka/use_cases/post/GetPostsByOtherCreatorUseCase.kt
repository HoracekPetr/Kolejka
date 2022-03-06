package com.example.kolejka.use_cases.post

import androidx.paging.PagingData
import com.example.kolejka.data.features.post.repository.PostRepository
import com.example.kolejka.models.Post
import kotlinx.coroutines.flow.Flow

class GetPostsByOtherCreatorUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(userId: String): Flow<PagingData<Post>> {
        return repository.postsByOtherCreator(userId)
    }
}