package com.todo.notes.data.repositories

import com.todo.notes.data.local.room.UserDao
import com.todo.notes.data.model.NoteDM
import javax.inject.Inject

/**
 * Class that handles Add Note credentials.
 */
class NotesRepository @Inject constructor(private val userDao: UserDao) {

    //insert user details to room
    suspend fun insertNote(note: NoteDM): Long {
        return userDao.insertNote(note)
    }

    //get all notes
     fun fetchNotes() = userDao.fetchNotes()

    //get single id based note
     fun fetchNote(id: Long) = userDao.fetchNote(id)

    //delete single user record
    suspend fun deleteNote(id: Long) {
        userDao.deleteNote(id)
    }

}