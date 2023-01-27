package com.zhvk.kolornotes.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhvk.kolornotes.data.repository.NoteRepository
import com.zhvk.kolornotes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val allNotes: MutableStateFlow<List<Note>> = MutableStateFlow<List<Note>>(listOf())
    val note: MutableStateFlow<Note> = MutableStateFlow<Note>(Note())

    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.getAllNotesFromRoom().collectLatest {
            allNotes.value = it // UI state is updated at this point
        }
    }

    // Similar method as the one above that uses collectLatest
    /*fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.getAllNotesFromRoom().onEach {
            allNotes.value = it // UI state is updated at this point
        }.launchIn(this)
    }*/

    fun getNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.getNoteFromRoom(id).collectLatest {
            note.value = it // UI state is updated at this point
        }
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.addNoteToRoom(note)

        // If you want to do your data loading on the IO thread, you need to switch back to the UI
        // thread after you've retrieved the data.
        withContext(Dispatchers.Main) {
            getAllNotes()
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.updateNoteInRoom(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNoteFromRoom(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotesFromRoom()
    }
}
