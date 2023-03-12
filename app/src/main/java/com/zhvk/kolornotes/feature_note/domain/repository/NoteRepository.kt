package com.zhvk.kolornotes.feature_note.domain.repository

import com.zhvk.kolornotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotesFromRoom(): Flow<List<Note>>

    fun getAllNotesFromRoomOrdered(): Flow<List<Note>>

    fun getNoteFromRoom(id: Long): Flow<Note>

    fun addNoteToRoom(note: Note): Long

    fun updateNoteInRoom(note: Note)

    fun deleteNoteFromRoom(note: Note)

    fun deleteAllNotesFromRoom()
}