package com.todo.notes.ui.home.add_note

import androidx.databinding.Bindable
import com.todo.notes.BR
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.base.BaseModel

/**
 * Data validation state of the add contact form.
 */

class AddNoteModel : BaseModel() {
    var noteId: Long? = null

    @get:Bindable
    var dataModel: NoteDM? = null
        set(value) {
            field = value
            handleUpdateCase()
            notifyPropertyChanged(BR.dataModel)
        }

    private fun handleUpdateCase() {
        title = dataModel?.title
        desc = dataModel?.description
        date = dataModel?.date
    }

    fun isUpdate() = dataModel != null
    fun getNoteDM() = NoteDM(title, desc, date, dataModel?.id ?: noteId)
    fun hasReminder() = date != null
    fun saveId(result: Long) {
        this.noteId = result
    }

    @get:Bindable
    var titleError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleError)
        }

    @get:Bindable
    var dateError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dateError)
        }

    @get:Bindable
    var descError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.descError)
        }

    @get:Bindable
    var desc: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.desc)
        }

    @get:Bindable
    var title: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var date: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

}