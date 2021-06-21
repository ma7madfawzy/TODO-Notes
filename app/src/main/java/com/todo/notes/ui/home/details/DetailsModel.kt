package com.todo.notes.ui.home.details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.todo.notes.BR
import com.todo.notes.data.model.NoteDM


class DetailsModel : BaseObservable() {

    var requestError: String? = null

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var dataModel: NoteDM? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataModel)
        }
}