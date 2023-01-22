package com.zhvk.kolornotes.data.repository

import androidx.lifecycle.MutableLiveData
import com.zhvk.kolornotes.data.network.NoteDao
import com.zhvk.kolornotes.domain.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteRepository(
    private val noteDao: NoteDao
) {
    val allNotes: List<Note> = noteDao.getAllNotes()
    val searchResults = MutableLiveData<List<Note>>()
    val searchResult = MutableLiveData<Note>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getAllNotesFromRoom() {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.getAllNotes()
        }
    }

    fun getNoteFromRoom(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.findNoteById(id)
        }
    }

    fun addNoteToRoom(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    fun updateNoteInRoom(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    fun deleteNoteFromRoom(note: Note) {
        coroutineScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }
}