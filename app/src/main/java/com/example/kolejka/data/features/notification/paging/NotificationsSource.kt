package com.example.kolejka.data.features.notification.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kolejka.data.features.notification.NotificationApi
import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.features.post.PostApi
import com.example.kolejka.data.util.Constants
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import com.example.kolejka.models.Post
import retrofit2.HttpException
import java.io.IOException

class NotificationsSource(
    private val api: NotificationApi
): PagingSource<Int, NotificationDto>() {

    private var currentPage = 0

    override fun getRefreshKey(state: PagingState<Int, NotificationDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, NotificationDto> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = api.getNotificationsForUser(page = nextPage, pageSize = DEFAULT_PAGE_SIZE)
            PagingSource.LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            ).also {
                currentPage++
            }
        } catch (e: IOException) {
            return PagingSource.LoadResult.Error(e)
        } catch (e: HttpException) {
            return PagingSource.LoadResult.Error(e)
        }
    }
}