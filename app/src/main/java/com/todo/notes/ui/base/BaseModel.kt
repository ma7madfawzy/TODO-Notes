package com.todo.notes.ui.base

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.todo.notes.BR

open class BaseModel : BaseObservable() {
    fun onSuccess() {
        messageText = null
        hideLoading()
    }

    fun hideLoading() {
        loading = false
    }

    fun showLoading() {
        loading = true
    }

    @get:Bindable
    var messageText: String? = null
        set(value) {
            hideLoading()
            field = value
            notifyPropertyChanged(BR.messageText)
        }

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }
}