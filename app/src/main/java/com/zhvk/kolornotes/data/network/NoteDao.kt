package com.zhvk.kolornotes.data.network

import androidx.room.*
import com.zhvk.kolornotes.domain.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE id=:id")
    fun findNoteById(id: Int): Note

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>
//    fun getAllNotes(): Flow<List<Note>>

//    @Query("SELECT * FROM notes ORDER BY date_updated DESC")
//    fun getAllOrdered(): List<Note>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Note>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    fun deleteAllNotes()
}