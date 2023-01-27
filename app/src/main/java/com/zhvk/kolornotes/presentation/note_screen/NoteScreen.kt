package com.zhvk.kolornotes.presentation.note_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.zhvk.kolornotes.getCurrentDateTime
import com.zhvk.kolornotes.presentation.main_screen.NotesViewModel
import com.zhvk.kolornotes.toString

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
            Log.d("NoteScreen.kt", "ID from NoteScreen(): " + noteId.toString())
            Note(navController, notesViewModel, noteId)
        }
        QuickActionBarNoteScreen()
    }
}

@Composable
fun Note(
    navController: NavController,
    notesViewModel: NotesViewModel,
    noteId: Int?
) {
    // Toolbar
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            // TODO OnBackPressed
            navController.popBackStack()
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        IconButton(onClick = {
            // TODO Save
        }) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }

    // Note
    if (noteId != null && noteId != 0)
        LaunchedEffect(Unit) {
            notesViewModel.getNote(noteId)
        }

    val noteState = notesViewModel.note.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
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
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = noteState.noteBody ?: "",
            onValueChange = { notesViewModel.updateNote(noteState.copy(noteBody = it)) },
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
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
fun QuickActionBarNoteScreen() {
    val date = getCurrentDateTime()
    val dateInString = date.toString("dd.MM.yyyy HH:mm") // TODO: Improve

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
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    }
}