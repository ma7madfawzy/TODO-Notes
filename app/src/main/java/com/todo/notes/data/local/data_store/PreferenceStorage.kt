package com.todo.notes.data.local.data_store

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {

    suspend fun hasNotes() : Flow<Boolean>
    suspend fun setHasNotes(order: Boolean)

}
