package com.zhvk.kolornotes.feature_note.presentation.note.components

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.presentation.note.NoteViewModel
import com.zhvk.kolornotes.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel = hiltViewModel(),
    noteId: Long?
) {
    // Get Note State
    if (noteId != null && noteId != 0L) // TODO: Test w/o this because noteId should always be passed
        LaunchedEffect(Unit) {
            noteViewModel.getNote(noteId)
        }
    val noteState = noteViewModel.note.collectAsState().value
    val context = LocalContext.current

    // BottomSheet State
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()
    /*BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }*/
    BackHandler(enabled = true, onBack = {
        if (sheetState.isVisible) coroutineScope.launch { sheetState.hide() }
        else goBack(navController, noteState, noteViewModel)
    })

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(noteState.color)),
        sheetState = sheetState,
        sheetContent = {
            ColorPickerBottomSheet(
                noteViewModel = noteViewModel,
                noteState = noteState
            )
        }) {

        // TODO: Let?
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Toolbar(goBack = { goBack(navController, noteState, noteViewModel) })
                NoteComposable(noteViewModel = noteViewModel, noteState = noteState)
            }
            QuickActionBar(
                lastUpdateDate = noteState.dateUpdated,
                shareNote = { shareNote(context, noteState) },
                openColorPickerBottomSheet = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                },
                goBackAndDeleteNote = {
                    navController.navigateUp()
                    noteViewModel.deleteNote(noteState) // TODO: Why the f is this crashing?! Because navigateUp takes some time to complete? But it's on main thread? :head_explode:
                })
        }
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

// TODO: maybe I should delete Note on the MainScreen?
fun goBack(navController: NavController, noteState: Note, noteViewModel: NoteViewModel) {
    navController.navigateUp()
    if (noteState.noteTitle.isNullOrEmpty() && noteState.noteBody.isNullOrEmpty())
        noteViewModel.deleteNote(noteState)
}