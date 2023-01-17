package com.zhvk.kolornotes.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "note_title")
    val noteTitle: String?,

    @ColumnInfo(name = "note_text")
    val noteText: String?,

    @ColumnInfo(name = "date_updated")
    val dateUpdated: String,

    @ColumnInfo(name = "image_path")
    val imagePath: String?,

    @ColumnInfo(name = "color")
    val color: String?,

    @ColumnInfo(name = "web_url")
    val webUrl: String?
) {

}