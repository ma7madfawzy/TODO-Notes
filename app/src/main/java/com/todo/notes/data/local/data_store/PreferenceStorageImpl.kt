package com.todo.notes.data.local.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Singleton
class PreferenceStorage @Inject constructor(context: Context) : PreferencesStorage {
    private val dataStore = context.dataStore

    //preference keys
    private object PreferencesKeys {
        val SAVED_KEY = booleanPreferencesKey("hasNotes")
    }

    //get saved key
    override suspend fun hasNotes() = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKeys.SAVED_KEY] ?: false
    }

    //set saved key
    override suspend fun setHasNotes(order: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.SAVED_KEY] = order
        }
    }

}