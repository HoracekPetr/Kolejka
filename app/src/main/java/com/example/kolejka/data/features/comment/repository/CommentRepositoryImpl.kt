package com.example.kolejka.data.features.comment.repository

import com.example.data.requests.CreateCommentRequest
import com.example.kolejka.R
import com.example.kolejka.data.features.comment.CommentApi
import com.example.kolejka.data.util.Resource
import com.example.kolejka.data.util.SimpleResource
import com.example.kolejka.models.Comment
import com.example.kolejka.view.util.uitext.UiText
import retrofit2.HttpException
import java.io.IOException

class CommentRepositoryImpl(
    private val commentApi: CommentApi
) : CommentRepository {

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        return try {

            val response = commentApi.getCommentsForPost(postId)

            if (response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(
                        uiText = UiText.StringDynamic(msg)
                    )
                } ?: Resource.Error(UiText.StringResource(R.string.an_unknown_error_occured))
            }
        } catch (e: IOException) {
            Resource.Error(uiText = UiText.StringResource(R.string.cant_reach_server))
        } catch (e: HttpException) {
            Resource.Error(uiText = UiText.StringResource(R.string.something_went_wrong))
        }
    }

    override suspend fun createComment(comment: String, postId: String): SimpleResource {

        val request = CreateCommentRequest(comment, postId)

        return try {
            val response = commentApi.createComment(request)
            if (response.successful) {
                Resource.Success(Unit)
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