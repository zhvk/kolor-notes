package com.zhvk.kolornotes.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Note UI State
@Entity(tableName = "notes")
public data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null, // TODO: Is this good?

    @ColumnInfo(name = "note_title")
    val noteTitle: String? = null,

    @ColumnInfo(name = "note_text")
    val noteText: String? = null,

    @ColumnInfo(name = "date_updated")
    val dateUpdated: String? = null,

    @ColumnInfo(name = "image_path")
    val imagePath: String? = null,

//    @ColumnInfo(name = "color")
//    @Ignore
//    val backgroundColor: Color? = null,

    @ColumnInfo(name = "web_url")
    val webUrl: String? = null
)