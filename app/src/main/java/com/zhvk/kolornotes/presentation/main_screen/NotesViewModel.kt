package com.zhvk.kolornotes.presentation.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zhvk.kolornotes.data.repository.NoteRepository
import com.zhvk.kolornotes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val allNotes: List<Note> = noteRepository.allNotes // TODO: OVDE PUCA SADA
    val foundNote: LiveData<Note> = noteRepository.searchResult
    val foundNotes: LiveData<List<Note>> = noteRepository.searchResults

    fun getAllNotes() {
        noteRepository.getAllNotesFromRoom()
    }

    fun getNote(id: Int) {
        noteRepository.getNoteFromRoom(id)
    }

    fun addNote(note: Note) {
        noteRepository.addNoteToRoom(note)
        getAllNotes()
    }

    fun updateNote(note: Note) {
        noteRepository.updateNoteInRoom(note)
        getAllNotes()
    }

    fun deleteNote(note: Note) {
        noteRepository.deleteNoteFromRoom(note)
        getAllNotes()
    }
}

/*
class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
