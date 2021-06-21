package com.todo.notes.data.repositories

import com.todo.notes.data.data_sources.ArticlesDataSource
import javax.inject.Inject

/**
 * Class that requests fetchArticles on the remote data source.
 */

class ArticlesRepository@Inject constructor(val dataSource: ArticlesDataSource) :BaseRepository(){

    suspend fun fetchArticles(
        q: String = "",
        page:Int,
        sortBy: String = "popularity",
        source: String = "bbc-news"
    ) = dataSource.fetchArticles(q, sortBy, source,page)
}