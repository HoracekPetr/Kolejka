package com.example.kolejka.data.features.news.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kolejka.data.features.news.NewsApi
import com.example.kolejka.data.features.news.dto.NewsDto
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class NewsSource(
    private val newsApi: NewsApi
): PagingSource<Int, NewsDto>() {
    override fun getRefreshKey(state: PagingState<Int, NewsDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, NewsDto> {
        return try{
            val page = params.key ?: 0
            val news = newsApi.getNews(page = page, pageSize = DEFAULT_PAGE_SIZE)

            PagingSource.LoadResult.Page(
                data = news.data ?: emptyList(),
                prevKey = if(page == 0) null else page-1,
                nextKey = if(news.data?.isEmpty() == true) null else page+1
            )
        } catch (e: IOException) {
            return PagingSource.LoadResult.Error(e)
        } catch (e: HttpException) {
            return PagingSource.LoadResult.Error(e)
        }
    }
}