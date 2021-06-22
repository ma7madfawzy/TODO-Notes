package com.example.contacts.ui.home.add_contact

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.todo.notes.BR

/**
 * Data validation state of the add contact form.
 */

class AddNoteModel : BaseObservable() {

    var requestError: String? = null

    @get:Bindable
    var titleError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleError)
        }

    @get:Bindable
    var descError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.descError)
        }

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var title: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var desc: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.desc)
        }

}