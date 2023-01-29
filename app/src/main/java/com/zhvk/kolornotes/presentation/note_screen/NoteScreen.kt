package com.zhvk.kolornotes.presentation.note_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.presentation.main_screen.NotesViewModel

@Composable
fun NoteScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel(),
    noteId: Int?
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            Toolbar(goBackAndSaveNote = {
//                TODO: Save note
                navController.navigateUp()
            })

            // TODO: Rade i 1. i 2. NoteComposable kako treba... E sad treba da nadjem resenje da mi
            //  ne trebaju 2 funkcije nego jedna zato sto mi treba objekat Note-a i za Toolbar i za
            //  QuickActionBar
            if (noteId != null && noteId != 0) NoteComposable(navController, notesViewModel, noteId)
            else NoteComposable(navController, notesViewModel)

        }
        QuickActionBarNoteScreen(null, {})
    }
}

@Composable
fun NoteComposable(
    navController: NavController,
    notesViewModel: NotesViewModel,
    noteId: Int
) {
    // Get Note State
//    if (noteId != null && noteId != 0)
    LaunchedEffect(Unit) {
        notesViewModel.getNote(noteId)
    }
    val noteState = notesViewModel.note.collectAsState().value

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
            value = noteState.noteTitle ?: "",
            onValueChange = { notesViewModel.updateNote(noteState.copy(noteTitle = it)) },
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.note_title_hint)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        // Note body
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = noteState.noteBody ?: "",
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
                backgroundColor = Color.Red,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun NoteComposable(
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    val noteTitleInitialValue = remember { mutableStateOf("") }
    val noteBodyInitialValue = remember { mutableStateOf("") }

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
            value = noteTitleInitialValue.value,
            onValueChange = { noteTitleInitialValue.value = it },
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.note_title_hint)
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        // Note body
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = noteBodyInitialValue.value,
            onValueChange = { noteBodyInitialValue.value = it },
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

        TextButton(
            onClick = {
                val note = Note(
                    id = 0,
                    noteTitle = noteTitleInitialValue.value,
                    noteBody = noteBodyInitialValue.value
                )
                notesViewModel.addNote(note)
            }
        ) {
            Text(
                text = "Add Note"
            )
        }
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
    goBackAndSaveNote: () -> Unit
) {
    val dateInString = lastUpdateDate ?: "a few seconds ago"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colorResource(id = R.color.mint_cream)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "")
        }
        Text(modifier = Modifier.padding(4.dp, 0.dp), text = "Edited $dateInString")
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = goBackAndSaveNote) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    }
}