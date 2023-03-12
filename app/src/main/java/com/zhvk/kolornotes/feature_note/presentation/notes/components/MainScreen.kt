package com.zhvk.kolornotes.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.presentation.notes.NotesViewModel

@Composable
fun MainScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {

    // Load Notes each time you get on this screen
    notesViewModel.getAllNotes()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.cultured)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchCard({})
            NoteCardList(notesViewModel, navController)
        }
        QuickActionBar(navController, notesViewModel)
    }
}

@Composable
@Preview
fun SimpleTextField() {
    val textState = remember { mutableStateOf("initial_value") }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it }
    )
}

// TODO: Populate Database only if DB is empty
/*@Composable
fun PopulateDatabase(
    notesViewModel: NotesViewModel
) {
    // Delete all content here.
//    notesViewModel.deleteAllNotes()

    // Add sample notes.
    for (note in getNotesComposable()) {
        notesViewModel.addNote(note)
    }
}*/
