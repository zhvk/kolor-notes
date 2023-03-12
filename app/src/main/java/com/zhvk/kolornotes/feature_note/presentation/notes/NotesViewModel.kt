package com.zhvk.kolornotes.feature_note.presentation.notes

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.repository.NoteRepository
import com.zhvk.kolornotes.feature_note.domain.use_case.NoteOrder
import com.zhvk.kolornotes.feature_note.domain.use_case.OrderType
import com.zhvk.kolornotes.utils.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val allNotes: MutableStateFlow<List<Note>> = MutableStateFlow<List<Note>>(listOf())
    val isFilterVisible = mutableStateOf(false)
    val noteOrder = mutableStateOf(NoteOrder.DESCENDING)
    val orderType = mutableStateOf(OrderType.DATE)

    private var recentlyDeletedNote: Note? = null

    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.getAllNotesFromRoomOrdered().collectLatest {
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

    fun addBlankNote(): Long {
        val newNote = Note()
        newNote.dateUpdated = getCurrentDateTime(null)
        newNote.color = Note.noteColors.random().toArgb()
        return noteRepository.addNoteToRoom(newNote)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNoteFromRoom(note)
        recentlyDeletedNote = note
    }

    fun restoreNote() {
        recentlyDeletedNote?.let {
            addNote(it)
            recentlyDeletedNote = null
        }
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteAllNotesFromRoom()
    }

    fun toggleFilter() {
        isFilterVisible.value = !isFilterVisible.value
    }

    fun showFilter(show: Boolean) {
        isFilterVisible.value = show
    }

    fun noteOrder(newOrder: NoteOrder) {
        noteOrder.value = newOrder
    }

    fun noteOrder(latestFirst: Boolean) {
        if (latestFirst) noteOrder.value = NoteOrder.DESCENDING
        else noteOrder.value = NoteOrder.ASCENDING
    }

    fun orderType(type: OrderType) {
        orderType.value = type
    }
}
