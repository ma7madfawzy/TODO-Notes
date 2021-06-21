package com.todo.notes.ui.home.details

import android.os.Bundle
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor() : BaseViewModel() {

    val model = DetailsModel()

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch(Dispatchers.IO) {
            model.loading = true
            model.dataModel = fetchExtras(extras)
            model.loading = false
        }
    }

    private suspend fun fetchExtras(extras: Bundle?): NoteDM =
        extras?.getParcelable("dataModel")!!


}