package com.todo.notes.ui.home.add_note

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.repositories.NotesRepository
import com.todo.notes.data.repositories.StoreRepository
import com.todo.notes.ui.base.BaseViewModel
import com.todo.notes.ui.home.details.DetailsViewModel
import com.todo.notes.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepository, private val storeRepo: StoreRepository
) : BaseViewModel() {

    val model = AddNoteModel()
    val saveNoteResult = MutableLiveData<Long>()


    fun onSaveNote() {
        if (validateData()) {
            onDataValid()
        }
    }

    private fun onDataValid() {
        if (model.isUpdate())
            updateNote()
        else
            insertNote()
    }


    //insert note to room database
    private fun updateNote() {
        coroutineScope.launch(Dispatchers.IO) {
            model.showLoading()
            storeRepo.setHasNotes(true)
            saveNoteResult.postValue(repository.updateNote(model.getNoteDM()))
            model.hideLoading()
        }
    }

    //insert note to room database
    private fun insertNote() {
        coroutineScope.launch(Dispatchers.IO) {
            model.showLoading()
            storeRepo.setHasNotes(true)
            val result=repository.insertNote(model.getNoteDM())
            model.saveId(result)
            saveNoteResult.postValue(result)
            model.hideLoading()

        }
    }

    fun readExtras(extras: Bundle?) {
        coroutineScope.launch {
            model.loading = true
            model.dataModel = fetchExtrasAsync(extras)
            model.loading = false
        }
    }

    private suspend fun fetchExtrasAsync(extras: Bundle?): NoteDM? {
        return withContext(Dispatchers.IO) { extras?.getParcelable(DetailsViewModel.DATA_MODEL) }
    }

    private fun validateData(): Boolean {
        var valid = true
        if (!ValidationUtils.isValidContentData(model.desc)) {
            model.descError = R.string.invalid_data
            valid = false
        }
        if (!ValidationUtils.isValidContentData(model.title)) {
            model.titleError = R.string.invalid_data
            valid = false
        }

        return valid
    }


}