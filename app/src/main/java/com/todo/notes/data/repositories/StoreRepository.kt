package com.todo.notes.data.repositories

import com.todo.notes.data.local.data_store.PreferenceStorage
import javax.inject.Inject

class StoreRepository @Inject constructor(private val preferenceStorage: PreferenceStorage) {
    suspend fun hasNotes() = preferenceStorage.hasNotes()

    suspend fun setHasNotes(value: Boolean) = preferenceStorage.setHasNotes(value)
}