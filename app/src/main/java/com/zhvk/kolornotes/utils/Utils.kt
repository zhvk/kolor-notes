package com.zhvk.kolornotes.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.model.NoteColor
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(format: String?, locale: Locale = Locale.getDefault()): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format ?: "dd.MM.yyyy HH:mm", locale)
    return formatter.format(time)
}

// TODO: Is there a better solution? There should be but I'm not sure how to store Color object in DB
@Composable
fun noteColorValue(colorEnum: NoteColor?): androidx.compose.ui.graphics.Color {
    return when (colorEnum) {
        NoteColor.WHITE -> colorResource(R.color.white)
        NoteColor.PURPLE -> colorResource(R.color.pastel_purple)
        NoteColor.RED -> colorResource(R.color.pastel_red)
        NoteColor.YELLOW -> colorResource(R.color.pastel_yellow)
        NoteColor.GREEN -> colorResource(R.color.pastel_green)
        NoteColor.BLUE -> colorResource(R.color.pastel_blue)
        else -> {
            // TODO: Is there a better solution for default return?
            return colorResource(R.color.white)
        }
    }
}

@Composable
fun getNotesComposable(): List<Note> {
    val note1 = Note(
        0,
        "The Dark Knight Rises", stringResource(id = R.string.batman_ipsum_1),
        "", NoteColor.PURPLE, ""
    )
    val note2 = Note(
        0,
        "Batman Forever", stringResource(id = R.string.batman_ipsum_2),
        "", NoteColor.RED, ""
    )
    val note3 = Note(
        0,
        "The Dark Knight", stringResource(id = R.string.batman_ipsum_3),
        "", NoteColor.YELLOW, ""
    )
    val note4 = Note(
        0,
        "Justice league", stringResource(id = R.string.batman_ipsum_4),
        "", NoteColor.GREEN, ""
    )
    val note5 = Note(
        0,
        "The Killing Joke", stringResource(id = R.string.batman_ipsum_5),
        "", NoteColor.BLUE, ""
    )
    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
}

fun getNotesFromContext(context: Context): List<Note> {
    val note1 = Note(
        0, "The Dark Knight Rises", context.getString(R.string.batman_ipsum_1),
        "", NoteColor.PURPLE, "", ""
    )
    val note2 = Note(
        0, "Batman Forever", context.getString(R.string.batman_ipsum_2),
        "", NoteColor.RED, "", ""
    )
    val note3 = Note(
        0, "The Dark Knight", context.getString(R.string.batman_ipsum_3),
        "", NoteColor.YELLOW, "", ""
    )
    val note4 = Note(
        0, "Justice league", context.getString(R.string.batman_ipsum_4),
        "", NoteColor.GREEN, "", ""
    )
    val note5 = Note(
        0, "The Killing Joke", context.getString(R.string.batman_ipsum_5),
        "", NoteColor.BLUE, "", ""
    )
    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
}