package com.zhvk.kolornotes.data.repository

import com.zhvk.kolornotes.data.network.NoteDao
import com.zhvk.kolornotes.domain.model.Note

class NoteRepository(
    private val noteDao: NoteDao
) {
    fun getAllNotesFromRoom() = noteDao.getAllNotes()

    fun getNoteFromRoom(id: Int) = noteDao.findNoteById(id)

    fun addNoteToRoom(note: Note) = noteDao.insertNote(note)

    fun updateNoteInRoom(note: Note) = noteDao.updateNote(note)

    fun deleteNoteFromRoom(note: Note) = noteDao.deleteNote(note)

    fun deleteAllNotesFromRoom() = noteDao.deleteAllNotes()
}