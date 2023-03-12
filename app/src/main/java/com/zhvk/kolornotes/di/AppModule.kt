package com.zhvk.kolornotes.di

import android.content.Context
import androidx.room.Room
import com.zhvk.kolornotes.core.Constants.Companion.NOTES_DB
import com.zhvk.kolornotes.feature_note.data.data_source.NoteDatabase
import com.zhvk.kolornotes.feature_note.data.repository.NoteRepositoryImpl
import com.zhvk.kolornotes.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTES_DB
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()

    /*@Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository =
        NoteRepositoryImpl(noteDao = noteDao)*/

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository =
        NoteRepositoryImpl(noteDatabase.noteDao())

}