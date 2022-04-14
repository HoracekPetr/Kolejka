package com.example.kolejka.data.features.news.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kolejka.R
import com.example.kolejka.data.features.news.NewsApi
import com.example.kolejka.data.features.news.dto.NewsDto
import com.example.kolejka.data.features.news.paging.NewsSource
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import com.example.kolejka.data.util.Constants.NEWS_UPDATE_DELAY
import com.example.kolejka.data.util.Constants.NOTIFICATION_UPDATE_DELAY
import com.example.kolejka.data.util.Resource
import com.example.kolejka.view.util.uitext.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {

    override val news: Flow<PagingData<NewsDto>>
        get() = Pager(PagingConfig(pageSize = DEFAULT_PAGE_SIZE)){
            NewsSource(newsApi = newsApi)
        }.flow


    override suspend fun getNewsById(newsId: String): Resource<NewsDto> {
        return try {

            val response = newsApi.getNewsById(newsId)

            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(uiText = UiText.StringDynamic(msg))
                }
                    ?: Resource.Error(uiText = UiText.StringResource(R.string.an_unknown_error_occured))
            }


        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }
}