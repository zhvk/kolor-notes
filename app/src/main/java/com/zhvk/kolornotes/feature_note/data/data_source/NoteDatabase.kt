package com.zhvk.kolornotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhvk.kolornotes.feature_note.domain.model.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}