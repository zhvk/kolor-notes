package com.zhvk.kolornotes.feature_note.data.repository

import com.zhvk.kolornotes.feature_note.data.data_source.NoteDao
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotesFromRoom() = noteDao.getAllNotes()

    override fun getNoteFromRoom(id: Long) = noteDao.findNoteById(id)

    override fun addNoteToRoom(note: Note) : Long = noteDao.insertNote(note)

    override fun updateNoteInRoom(note: Note) = noteDao.updateNote(note)

    override fun deleteNoteFromRoom(note: Note) = noteDao.deleteNote(note)

    override fun deleteAllNotesFromRoom() = noteDao.deleteAllNotes()
}