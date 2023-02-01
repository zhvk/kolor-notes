package com.zhvk.kolornotes.presentation.note_screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.noteColorValue
import com.zhvk.kolornotes.presentation.main_screen.NotesViewModel

@Composable
fun NoteScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel(),
    noteId: Long?
) {
    // Get Note State
    if (noteId != null && noteId != 0L) // TODO: Test w/o this because noteId should always be passed
        LaunchedEffect(Unit) {
            notesViewModel.getNote(noteId)
        }
    val noteState = notesViewModel.note.collectAsState().value
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(noteColorValue(noteState.backgroundColor)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(goBackAndSaveNote = { navController.navigateUp() })
            NoteComposable(notesViewModel = notesViewModel, noteState = noteState)
        }
        QuickActionBarNoteScreen(
            lastUpdateDate = noteState.dateUpdated,
            shareNote = { shareNote(context, noteState) },
            goBackAndDeleteNote = {
                navController.navigateUp()
                notesViewModel.deleteNote(noteState) // TODO: Why the f is this crashing?!
            })
    }
}

fun shareNote(context: Context, note: Note) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, note.noteTitle + "\n" + note.noteBody)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

@Composable
fun NoteComposable(
    notesViewModel: NotesViewModel,
    noteState: Note
) {

    // Note UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // Note title
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp),
            value = noteState.noteTitle!! /*noteState.noteTitle ?: ""*/,
            onValueChange = { notesViewModel.updateNote(noteState.copy(noteTitle = it)) },
            textStyle = TextStyle.Default.copy(fontSize = 24.sp),
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.note_title_hint),
                        fontSize = 24.sp
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        // Note body
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = noteState.noteBody!! /*noteState.noteBody ?: ""*/,
            onValueChange = { notesViewModel.updateNote(noteState.copy(noteBody = it)) },
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.note_body_hint)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun Toolbar(
    goBackAndSaveNote: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = goBackAndSaveNote) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        /*IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }*/
    }
}

@Composable
fun QuickActionBarNoteScreen(
    lastUpdateDate: String?,
    shareNote: () -> Unit,
    goBackAndDeleteNote: () -> Unit
) {
    val dateInString = lastUpdateDate ?: "a few seconds ago"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = shareNote) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "")
        }
        Text(modifier = Modifier.padding(4.dp, 0.dp), text = "Edited $dateInString")
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = goBackAndDeleteNote) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    }
}