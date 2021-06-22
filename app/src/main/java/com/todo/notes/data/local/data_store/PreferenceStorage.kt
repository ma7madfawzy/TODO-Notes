package com.todo.notes.data.local.data_store

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {

    suspend fun savedKey() : Flow<Boolean>
    suspend fun setSavedKey(order: Boolean)

}
