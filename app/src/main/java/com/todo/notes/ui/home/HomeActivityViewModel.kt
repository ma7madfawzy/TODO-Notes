package com.todo.notes.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.repositories.NotesRepository
import com.todo.notes.data.repositories.StoreRepository
import com.todo.notes.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivityViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val storeRepo: StoreRepository
) : BaseViewModel() {
    val model = HomeActivityModel()
    val notesData = MediatorLiveData<List<NoteDM>>()
    val hasNotes = MutableLiveData<Boolean>()

    init {
        checkHasNotes()
    }

    //insert note to room database
    private fun checkHasNotes() {
        model.showLoading()
        coroutineScope.launch(Dispatchers.IO) {
            storeRepo.hasNotes()
                .onStart { model.showLoading() }
                .onCompletion { model.hideLoading() }
                .catch { model.messageText = it.message }
                .collect {
                    hasNotes.postValue(it)
                    model.onSuccess()
                }
        }
    }

    private fun searchNotes(queryString: String) {
        // can be launched in a separate asynchronous job
        coroutineScope.launch(Dispatchers.IO) {
            repository.fetchNotes(queryString)
                .onStart { model.showLoading() }
                .catch { model.messageText = it.message }
                .collect {
                    notesData.postValue(it)
                    model.onSuccess()
                }
        }
    }

    //delete based id note
    fun deleteNote(id: Long?) {
        coroutineScope.launch(Dispatchers.IO) {
            model.showLoading()
            repository.deleteNote(id)
            model.hideLoading()
            loadData()
        }
    }

    fun loadData() {
        searchNotes(model.queryText.toString())
    }

    fun onQueryChanged(query: String) {
        model.queryText = query
        loadData()
    }
}