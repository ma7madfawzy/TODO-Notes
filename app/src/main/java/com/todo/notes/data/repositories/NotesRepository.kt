package com.todo.notes.data.repositories

import com.todo.notes.data.local.room.UserDao
import com.todo.notes.data.model.NoteDM
import javax.inject.Inject

/**
 * Class that handles Add Note credentials.
 */
class NotesRepository @Inject constructor(private val userDao: UserDao) {

    //insert note to room
    suspend fun insertNote(note: NoteDM) = userDao.insertNote(note)

    //update note to room
    suspend fun updateNote(note: NoteDM) :Long{
        userDao.updateNote(note)
        return 0L
    }

    //get all notes
    private fun fetchNotes() = userDao.fetchNotes()

    //get all notes wit alike title or desc
    fun fetchNotes(searchedText: String) =
        if (searchedText.trim().isEmpty()) fetchNotes()
        else userDao.fetchNotes(searchedText)

    //get single id based note
    fun fetchNote(id: Int) = userDao.fetchNote(id)

    //delete single user record
    suspend fun deleteNote(id: Long?) {
        userDao.deleteNote(id)
    }

}