package com.example.kolejka.data.features.post.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import com.example.kolejka.models.Post
import retrofit2.HttpException
import java.io.IOException

class AllPostsSource(
    private val api: PostApi
) : PagingSource<Int, Post>() {

    private var currentPage = 0

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = api.getPostsByAll(page = nextPage, pageSize = DEFAULT_PAGE_SIZE)
            LoadResult.Page(
                data = posts.reversed(),
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            ).also {
                currentPage++
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}

