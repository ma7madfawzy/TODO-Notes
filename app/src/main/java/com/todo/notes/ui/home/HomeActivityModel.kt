package com.todo.notes.ui.home

import com.todo.notes.ui.base.BaseModel

/**
 * Data validation state of the add contact form.
 */

class HomeActivityModel : BaseModel() {
    var queryText: String? = ""
    var showAlert: Boolean? = false
}