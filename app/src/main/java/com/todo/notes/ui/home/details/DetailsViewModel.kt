package com.todo.notes.ui.home.details

import android.os.Bundle
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.repositories.NotesRepository
import com.todo.notes.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: NotesRepository
) : BaseViewModel() {
    companion object{
        const val DATA_MODEL="dataModel"
    }

    val model = DetailsModel()

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch {
            model.loading = true
            model.dataModel = fetchExtrasAsync(extras)
            model.loading = false
        }
    }

    //delete based id note
    fun deleteNote() {
        coroutineScope.launch(Dispatchers.IO) {
            model.showLoading()
            repository.deleteNote(model.dataModel?.id)
            model.hideLoading()
        }
    }

    private suspend fun fetchExtrasAsync(extras: Bundle?): NoteDM? {
        return withContext(Dispatchers.IO) { extras?.getParcelable(DATA_MODEL) }
    }
}