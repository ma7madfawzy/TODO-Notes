package com.todo.notes.data.data_sources

import com.todo.notes.data.api.ApiService
import com.todo.notes.data.api.RetrofitBuilder
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.model.ResponseDM
import com.todo.notes.data.model.Result
import com.todo.notes.utils.Constants
import retrofit2.HttpException
import javax.inject.Inject

/**
 * DataSource class that works with local and remote data sources.
 */
class ArticlesDataSource @Inject constructor(val apiService: ApiService) {
    /**
     * Search  whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun fetchArticles(
        query: String, sortBy: String, source: String,
        page: Int
    ): Result<ResponseDM<List<NoteDM>>> {
        return requestData(query, sortBy, source, page)
    }

    private suspend fun requestData(query: String, sortBy: String, source: String, page: Int)
            : Result<ResponseDM<List<NoteDM>>> {
        return try {
            when (val result = getResultCall(query, sortBy, source, page)) {
                is Result.Success<ResponseDM<List<NoteDM>>> -> {
                    Result.Success(result.data)
                }
                is Result.Error -> {
                    Result.Error(result.exception)
                }
            }
        } catch (exception: HttpException) {
            Result.Error(exception.message.toString())
        }
    }

    private suspend fun getResultCall(
        query: String, sortBy: String, source: String, page: Int
    ): Result<ResponseDM<List<NoteDM>>> {
        return RetrofitBuilder.safeApiCall(call = {
            apiService.fetchNews(
                Constants.API_KEY, query, page, Constants.NETWORK_PAGE_SIZE, sortBy, source
            )
        })
    }
}