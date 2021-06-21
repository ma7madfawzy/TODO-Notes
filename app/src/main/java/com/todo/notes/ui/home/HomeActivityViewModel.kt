package com.todo.notes.ui.home

import androidx.lifecycle.MutableLiveData
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.model.ResponseDM
import com.todo.notes.data.model.Result
import com.todo.notes.data.repositories.ArticlesRepository
import com.todo.notes.ui.base.BaseLoaderAdapter
import com.todo.notes.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivityViewModel @Inject constructor(private val repository: ArticlesRepository) :
    BaseViewModel(),
    BaseLoaderAdapter.PaginationHandler {
    val model = HomeActivityModel()

    val newsResult = MutableLiveData<List<NoteDM>>()

    private fun searchRepo(queryString: String, page: Int = 1) {
        // can be launched in a separate asynchronous job
        model.loading = page == 1
        coroutineScope.launch(Dispatchers.IO) {
            onDataLoaded(repository.fetchArticles(queryString, page))
        }
    }

    private fun onDataLoaded(result: Result<ResponseDM<List<NoteDM>>>) {
        when (result) {
            is Result.Success<ResponseDM<List<NoteDM>>> -> onSuccess(result)
            is Result.Error -> onError(result)
        }
        model.loading = false
    }

    private fun onError(result: Result.Error) {
        //snack the error if theres is data already so the recycler is taking the whole screen
        model.showToast = newsResult.value != null || newsResult.value?.isNotEmpty() == true
        model.messageText = result.exception
        newsResult.postValue(emptyList())
    }

    private fun onSuccess(result: Result.Success<ResponseDM<List<NoteDM>>>) {
        result.data.articles.let { newsResult.postValue(it) }
        model.onSuccess()
    }

    override fun onLoadMore(page: Int, totalRows: Int) {
        loadData(page)
    }

    fun loadData(page: Int = 1) {
        searchRepo(model.queryText.toString(), page)
    }

    fun onQueryChanged(query: String) {
        model.queryText = query
        loadData(1)
    }
}