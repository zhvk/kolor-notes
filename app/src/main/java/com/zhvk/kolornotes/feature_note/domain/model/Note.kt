package com.zhvk.kolornotes.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhvk.kolornotes.core.Constants.Companion.NOTES_TABLE

// Note UI State
@Entity(tableName = NOTES_TABLE)
data class Note(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @ColumnInfo(name = "note_title") var noteTitle: String? = "nulle",
        @ColumnInfo(name = "note_text") var noteBody: String? = "nulle",
        @ColumnInfo(name = "date_updated") var dateUpdated: String? = null,
        @ColumnInfo(name = "color") var backgroundColor: NoteColor? = NoteColor.WHITE,
        @ColumnInfo(name = "image_path") var imagePath: String? = null,
        @ColumnInfo(name = "web_url") var webUrl: String? = null
)

data class NoteColorItem(
    val selected: Boolean = false,
    val color: NoteColor = NoteColor.WHITE
) {

}

enum class NoteColor() {
    WHITE, RED, YELLOW, GREEN, BLUE, PURPLE
}