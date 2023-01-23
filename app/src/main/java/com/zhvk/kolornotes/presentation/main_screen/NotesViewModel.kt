package com.zhvk.kolornotes.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhvk.kolornotes.data.repository.NoteRepository
import com.zhvk.kolornotes.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var allNotes by mutableStateOf(listOf<Note>())
        private set
    var note by mutableStateOf(Note())
        private set

    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        allNotes = noteRepository.getAllNotesFromRoom()
    }

    fun getNote(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        note = noteRepository.getNoteFromRoom(id)
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
//        getAllNotes()
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNoteFromRoom(note)
//        getAllNotes()
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotesFromRoom()
    }
}
