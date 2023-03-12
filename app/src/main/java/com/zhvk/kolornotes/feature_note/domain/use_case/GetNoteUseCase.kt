package com.zhvk.kolornotes.feature_note.domain.use_case

import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(orderType: OrderType, noteOrder: NoteOrder): Flow<List<Note>> {
        return noteRepository.getAllNotesFromRoom().map { notes ->
            when (noteOrder) {
                NoteOrder.ASCENDING -> {
                    when (orderType) {
                        OrderType.DATE -> notes.sortedBy { it.dateUpdated }
                        OrderType.TITLE -> notes.sortedBy { it.noteTitle }
                        OrderType.COLOR -> notes.sortedBy { it.backgroundColor }
                    }
                }
                NoteOrder.DESCENDING -> {
                    when (orderType) {
                        OrderType.DATE -> notes.sortedByDescending { it.dateUpdated }
                        OrderType.TITLE -> notes.sortedByDescending { it.noteTitle }
                        OrderType.COLOR -> notes.sortedByDescending { it.backgroundColor }
                    }
                }
            }
        }
    }
}

enum class NoteOrder {
    ASCENDING, DESCENDING
}

enum class OrderType {
    DATE, TITLE, COLOR
}