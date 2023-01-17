package com.zhvk.kolornotes.dao

import androidx.room.*
import com.zhvk.kolornotes.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>

//    @Query("SELECT * FROM notes ORDER BY date_updated DESC")
//    fun getAllOrdered(): List<Note>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<Note>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)
}