package com.zhvk.kolornotes.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(format: String?, locale: Locale = Locale.getDefault()): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat(format ?: "dd.MM.yyyy HH:mm", locale)
    return formatter.format(time)
}
@Composable
fun getNotesComposable(): List<Note> {
    val note1 = Note(
        0,
        "The Dark Knight Rises", stringResource(id = R.string.batman_ipsum_1),
        "", PastelPurple.toArgb(), ""
    )
    val note2 = Note(
        0,
        "Batman Forever", stringResource(id = R.string.batman_ipsum_2),
        "", PastelRed.toArgb(), ""
    )
    val note3 = Note(
        0,
        "The Dark Knight", stringResource(id = R.string.batman_ipsum_3),
        "", PastelYellow.toArgb(), ""
    )
    val note4 = Note(
        0,
        "Justice league", stringResource(id = R.string.batman_ipsum_4),
        "", PastelGreen.toArgb(), ""
    )
    val note5 = Note(
        0,
        "The Killing Joke", stringResource(id = R.string.batman_ipsum_5),
        "", PastelBlue.toArgb(), ""
    )
    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
}

fun getNotesFromContext(context: Context): List<Note> {
    val note1 = Note(
        0, "The Dark Knight Rises", context.getString(R.string.batman_ipsum_1),
        "", PastelPurple.toArgb(), "", ""
    )
    val note2 = Note(
        0, "Batman Forever", context.getString(R.string.batman_ipsum_2),
        "", PastelRed.toArgb(), "", ""
    )
    val note3 = Note(
        0, "The Dark Knight", context.getString(R.string.batman_ipsum_3),
        "", PastelYellow.toArgb(), "", ""
    )
    val note4 = Note(
        0, "Justice league", context.getString(R.string.batman_ipsum_4),
        "", PastelGreen.toArgb(), "", ""
    )
    val note5 = Note(
        0, "The Killing Joke", context.getString(R.string.batman_ipsum_5),
        "", PastelBlue.toArgb(), "", ""
    )
    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
}
