package com.example.contacts.ui.home.add_contact

import androidx.lifecycle.MutableLiveData
import com.todo.notes.R
import com.todo.notes.data.model.NoteDM
import com.todo.notes.data.repositories.NotesRepository
import com.todo.notes.data.repositories.StoreRepository
import com.todo.notes.ui.base.BaseViewModel
import com.todo.notes.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val repository: NotesRepository, storeRepo: StoreRepository) : BaseViewModel() {

    val model = AddNoteModel()

     val saveNoteResult = MutableLiveData<Long>()
    fun onSaveNote() {
        if (validateData()) {
            model.loading = true
            insertNote(getNoteDM())
        }
    }

    private fun getNoteDM() = NoteDM(model.title, model.desc, "date")

    //insert note to room database
    private fun insertNote(noteDM: NoteDM) {
        coroutineScope.launch(Dispatchers.IO) {

            saveNoteResult.postValue(repository.insertNote(noteDM))
        }
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