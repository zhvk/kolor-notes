package com.zhvk.kolornotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhvk.kolornotes.dao.NoteDao
import com.zhvk.kolornotes.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}