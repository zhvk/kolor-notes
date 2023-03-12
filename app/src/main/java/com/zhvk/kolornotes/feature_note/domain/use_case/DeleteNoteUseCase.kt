package com.zhvk.kolornotes.feature_note.domain.use_case

import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(note: Note) {
        return noteRepository.deleteNoteFromRoom(note)
    }
}