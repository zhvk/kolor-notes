package com.zhvk.kolornotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhvk.kolornotes.core.Constants
import com.zhvk.kolornotes.core.Screen
import com.zhvk.kolornotes.feature_note.presentation.notes.NotesViewModel

@Composable
fun NoteCardList(
    notesViewModel: NotesViewModel,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 0.dp, 6.dp, 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        items(notesViewModel.allNotes.value) { note ->
            NoteCardItem(note = note, onClicked = {
                navController.navigate(Screen.NoteScreen.route + "?${Constants.NOTE_ID}=${it}")
            })
        }
    }
}