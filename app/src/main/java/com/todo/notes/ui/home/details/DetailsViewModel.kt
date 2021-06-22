package com.todo.notes.ui.home.details

import android.os.Bundle
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.base.BaseViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class DetailsViewModel @Inject constructor() : BaseViewModel() {

    val model = DetailsModel()

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch {
            model.loading = true
            model.dataModel = fetchExtrasAsync(extras)
            model.loading = false
        }
    }

    private suspend fun fetchExtrasAsync(extras: Bundle?): NoteDM? {
        return withContext(Dispatchers.IO) { extras?.getParcelable("dataModel") }
    }
}