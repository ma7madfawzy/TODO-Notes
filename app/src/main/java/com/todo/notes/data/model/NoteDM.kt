package com.todo.notes.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_notes")
data class NoteDM(
    val title: String?,
    val description: String?,
    val date: String?
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long? = 0
}