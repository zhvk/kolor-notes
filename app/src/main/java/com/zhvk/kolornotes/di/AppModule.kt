package com.zhvk.kolornotes.di

import android.content.Context
import androidx.room.Room
import com.zhvk.kolornotes.core.Constants.Companion.NOTES_TABLE
import com.zhvk.kolornotes.data.network.NoteDao
import com.zhvk.kolornotes.data.network.NoteDatabase
import com.zhvk.kolornotes.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// TODO: FIND EXPLANATION FOR THE EXISTENCE OF THIS CLASS

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideNotesDatabase(
        @ApplicationContext
        context : Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTES_TABLE
    ).build()

    @Provides
    fun provideNotesDatabase2(@ApplicationContext context : Context): NoteDatabase {
        return NoteDatabase.getInstance(context, CoroutineScope(SupervisorJob()))
    }

    @Provides
    fun provideNoteDao(
        noteDatabase: NoteDatabase
    ) = noteDatabase.noteDao()

    @Provides
    fun provideBookRepository(
        noteDao: NoteDao
    ): NoteRepository = NoteRepository(
        noteDao = noteDao
    )
}