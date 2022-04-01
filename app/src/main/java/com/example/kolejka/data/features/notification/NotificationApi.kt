package com.example.kolejka.data.features.notification

import com.example.kolejka.data.features.notification.dto.NotificationDto
import com.example.kolejka.data.response.BasicApiResponse
import com.example.kolejka.data.util.Constants.DEFAULT_PAGE_SIZE
import com.example.kolejka.data.util.Resource
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApi {

    @GET("/api/notifications/get")
    suspend fun getNotificationsForUser(
        @Query("page") page: Int = 0,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE
    ): List<NotificationDto>

    @GET("/api/notifications/getCount")
    suspend fun getNotificationsCount(): Int

    @POST("/api/notifications/zero")
    suspend fun setNotificationsToZero(): Unit

    @DELETE("/api/notifications/delete")
    suspend fun deleteNotificationsForUser(): BasicApiResponse<Unit>
}