package com.zhvk.kolornotes.presentation.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhvk.kolornotes.data.repository.NoteRepository
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.domain.model.NoteColor
import com.zhvk.kolornotes.utils.getCurrentDateTime
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

    fun addNote(newNote: Note) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "Note to be added: $newNote")
        newNote.dateUpdated = getCurrentDateTime(null)
        val newId = noteRepository.addNoteToRoom(newNote)
        Log.d("TAG", "New ID: $newId")
        // If you want to do your data loading on the IO thread, you need to switch back to the UI
        // thread after you've retrieved the data.
        withContext(Dispatchers.Main) {
            getAllNotes()
        }
    }

    suspend fun addBlankNote(): Long {
        val newNote = Note()
        newNote.dateUpdated = getCurrentDateTime(null)
        newNote.backgroundColor = NoteColor.values().random()
        return noteRepository.addNoteToRoom(newNote)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotesFromRoom()
    }
}
