package com.zhvk.kolornotes.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhvk.kolornotes.core.Constants.Companion.NOTES_TABLE
import com.zhvk.kolornotes.ui.theme.*

// Note UI State
@Entity(tableName = NOTES_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") var noteTitle: String? = "nulle",
    @ColumnInfo(name = "note_text") var noteBody: String? = "nulle",
    @ColumnInfo(name = "date_updated") var dateUpdated: String? = null,
    @ColumnInfo(name = "background_color") var color: Int = 0xffffff,
    @ColumnInfo(name = "image_path") var imagePath: String? = null,
    @ColumnInfo(name = "web_url") var webUrl: String? = null
) {
    companion object {
        val noteColors =
            listOf(MyWhite, PastelRed, PastelYellow, PastelGreen, PastelBlue, PastelPurple)
    }
}

data class NoteColorItem(
    val selected: Boolean = false,
    val color: Color = MyWhite
)

/*
enum class NoteColor() {
    WHITE, RED, YELLOW, GREEN, BLUE, PURPLE
}*/
