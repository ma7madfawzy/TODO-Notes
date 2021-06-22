package com.todo.notes.ui.home.details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.todo.notes.BR
import com.todo.notes.data.model.NoteDM
import com.todo.notes.ui.base.BaseModel


class DetailsModel : BaseModel() {

    @get:Bindable
    var dataModel: NoteDM? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataModel)
        }
}