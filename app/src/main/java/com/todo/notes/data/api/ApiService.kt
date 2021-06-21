package com.todo.notes.data.api

import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.model.ResponseDM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("Everything")
    suspend fun
            fetchNews(
        @Query("apiKey") api_key: String,
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String,
        @Query("sources") source: String
    ): Response<ResponseDM<List<NoteDM>>>
}