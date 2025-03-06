package org.d3ifcool.twily.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addTodo (note: Note)

    @Query("SELECT * FROM note")
    suspend fun getNotes() : List<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note WHERE id=:note_id")
    suspend fun getNote(note_id: Int) : List<Note>
}