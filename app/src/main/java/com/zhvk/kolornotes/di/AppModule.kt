package com.zhvk.kolornotes.di

import android.content.Context
import androidx.room.Room
import com.zhvk.kolornotes.core.Constants.Companion.NOTES_DB
import com.zhvk.kolornotes.feature_note.data.data_source.NoteDao
import com.zhvk.kolornotes.feature_note.data.data_source.NoteDatabase
import com.zhvk.kolornotes.feature_note.data.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideNotesDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTES_DB
    ).fallbackToDestructiveMigration().build()

    /*@Provides
    fun provideNotesDatabase2(@ApplicationContext context : Context): NoteDatabase {
        return NoteDatabase.getInstance(context, CoroutineScope(SupervisorJob()))
    }*/

    @Provides
    fun provideNoteDao(
        noteDatabase: NoteDatabase
    ) = noteDatabase.noteDao()

    @Provides
    fun provideBookRepository(
        noteDao: NoteDao
    ): NoteRepositoryImpl = NoteRepositoryImpl(
        noteDao = noteDao
    )
}