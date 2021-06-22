package com.todo.notes.data.local.room

import androidx.room.*
import com.todo.notes.data.model.NoteDM
import kotlinx.coroutines.flow.Flow

/**
 * This class is used as a model, which represents a table in our database as Annotated with @Entity
 */
@Dao
interface UserDao {
    /**
     * CREATE
     */
    @Insert
    suspend fun insertNote(note: NoteDM): Long

    /**
     * READ
     */
    @Transaction
    @Query("SELECT * FROM user_notes")
    fun fetchNotes(): Flow<List<NoteDM>>

    //get all notes with searched text title/desc
    @Transaction
    @Query("SELECT * FROM user_notes WHERE title LIKE :searchText || description LIKE :searchText ")
    fun fetchNotes(searchText: String): Flow<List<NoteDM>>

    //get single note inserted to room database
    @Transaction
    @Query("SELECT * FROM user_notes WHERE id = :id")
    fun fetchNote(id: Int): Flow<NoteDM>

    /**
     * UPDATE
     */
    @Update
    suspend fun updateNote(note: NoteDM)

    /**
     * DELETE
     */
    @Query("DELETE FROM user_notes WHERE id = :id")
    suspend fun deleteNote(id: kotlin.Long?)

    //delete all note details
    @Delete
    suspend fun deleteAllNotesDetails(note: NoteDM)
}