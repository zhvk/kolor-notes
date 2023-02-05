package com.zhvk.kolornotes.presentation.note_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhvk.kolornotes.data.repository.NoteRepository
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.domain.model.NoteColor
import com.zhvk.kolornotes.domain.model.NoteColorItem
import com.zhvk.kolornotes.utils.getCurrentDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    val note: MutableStateFlow<Note> = MutableStateFlow<Note>(Note())

    val noteColorItems: MutableStateFlow<List<NoteColorItem>> =
        MutableStateFlow<List<NoteColorItem>>(setColorItems())

    fun getNote(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.getNoteFromRoom(id).collectLatest {
            note.value = it // UI state is updated at this point
            Log.d("TAG", "Note gotten: " + note.value.toString())
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        note.dateUpdated = getCurrentDateTime(null)
        noteRepository.updateNoteInRoom(note)
        Log.d("TAG", "Note updated")
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.deleteNoteFromRoom(note)
    }

    fun updateNoteColor(note: Note, noteColor: NoteColor) {
        note.backgroundColor = noteColor
        updateNote(note)
    }

    private fun setColorItems() : MutableList<NoteColorItem> {
        val noteColorItems: MutableList<NoteColorItem> = mutableListOf()
        val noteColors = NoteColor.values()

        noteColors.forEach {
            noteColorItems.add(NoteColorItem(false, it))
        }
        return noteColorItems
    }
}