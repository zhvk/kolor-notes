package com.zhvk.kolornotes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.zhvk.kolornotes.domain.model.Note
import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun Date.toString(format: String?, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format ?: "dd.MM.yyyy HH:mm", locale)
    return formatter.format(this)
}

@Composable
fun getNotesComposable(): List<Note> {
    val note1 = Note(1,
        "The Dark Knight Rises", stringResource(id = R.string.batman_ipsum_1),
        "", "", /*pastel_purple,*/ ""
    )
    val note2 = Note(2,
        "Batman Forever", stringResource(id = R.string.batman_ipsum_2),
        "", "", /*pastel_red,*/ ""
    )
    val note3 = Note(3,
        "The Dark Knight", stringResource(id = R.string.batman_ipsum_3),
        "", "", /*pastel_yellow,*/ ""
    )
    val note4 = Note(4,
        "Justice league", stringResource(id = R.string.batman_ipsum_4),
        "", "", /*pastel_green,*/ ""
    )
    val note5 = Note(5,
        "The Killing Joke", stringResource(id = R.string.batman_ipsum_5),
        "", "", /*pastel_blue,*/ ""
    )
    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
}

//fun getNotesFromContext(context: Context): List<Note> {
//    val note1 = Note(
//        "The Dark Knight Rises", context.getString(R.string.batman_ipsum_1),
//        "", "", /*pastel_purple,*/ ""
//    )
//    val note2 = Note(
//        "Batman Forever", context.getString(R.string.batman_ipsum_2),
//        "", "", /*pastel_red,*/ ""
//    )
//    val note3 = Note(
//        "The Dark Knight", context.getString(R.string.batman_ipsum_3),
//        "", "", /*pastel_yellow,*/ ""
//    )
//    val note4 = Note(
//        "Justice league", context.getString(R.string.batman_ipsum_4),
//        "", "", /*pastel_green,*/ ""
//    )
//    val note5 = Note(
//        "The Killing Joke", context.getString(R.string.batman_ipsum_5),
//        "", "", /*pastel_blue,*/ ""
//    )
//    return listOf(note1, note3, note5, note2, note4, note5, note2, note1, note3, note4)
//}