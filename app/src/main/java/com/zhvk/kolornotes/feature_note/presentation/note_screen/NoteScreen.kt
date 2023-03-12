package com.zhvk.kolornotes.feature_note.presentation.note_screen

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.domain.model.NoteColor
import com.zhvk.kolornotes.ui.theme.*
import com.zhvk.kolornotes.utils.noteColorValue
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
            .background(noteColorValue(noteState.backgroundColor)),
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
            QuickActionBarNoteScreen(
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

@Composable
fun Toolbar(
    goBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = goBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    }
}

@Composable
fun NoteComposable(
        noteViewModel: NoteViewModel,
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
            onValueChange = { noteViewModel.updateNote(noteState.copy(noteTitle = it)) }, // TODO: Improve, this should be handled in ViewModel
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
            onValueChange = { noteViewModel.updateNote(noteState.copy(noteBody = it)) },
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
fun QuickActionBarNoteScreen(
    lastUpdateDate: String?,
    shareNote: () -> Unit,
    openColorPickerBottomSheet: () -> Unit,
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
        Row() {
            IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = shareNote) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "")
            }
            IconButton(
                modifier = Modifier.padding(4.dp, 0.dp),
                onClick = openColorPickerBottomSheet
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = ""
                ) // TODO: Find Palette icon
            }
        }
        Text(modifier = Modifier.padding(4.dp, 0.dp), text = "Edited $dateInString")
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = goBackAndDeleteNote) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
        }
    }
}

@Composable
fun ColorPickerBottomSheet(
        noteViewModel: NoteViewModel,
        noteState: Note
) {

    Column() {
        Text(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
            text = stringResource(id = R.string.note_pick_color_header)
        )
        LazyRow(
            modifier = Modifier.selectableGroup(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            for (noteColorOption in NoteColor.values()) {
                item {
                    Box(
                        modifier = Modifier.selectable(
                            selected = (noteState.backgroundColor == noteColorOption),
                            onClick = {
                                noteState.backgroundColor = noteColorOption
                                noteViewModel.updateNote(noteState)
                                Log.d("TAG", "UPDATE NOTE")
                            },
                            role = Role.RadioButton
                        )
                    ) {
                        RoundedCheckView(
                            selectedItem = noteState.backgroundColor!!,
                            noteColorOption = noteColorOption
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun RoundedCheckView(
        selectedItem: NoteColor,
        noteColorOption: NoteColor,
) {
    val isSelected = remember { mutableStateOf(false) }
    val circleThickness = remember { mutableStateOf(1.dp) }
    val borderColor = remember { mutableStateOf(Color.LightGray) }
    val isCheckedTint = colorResource(id = R.color.midnight_green_eagle_green)

    isSelected.value = selectedItem == noteColorOption
    if (isSelected.value) {
        circleThickness.value = 2.dp
        borderColor.value = isCheckedTint
    } else {
        circleThickness.value = 1.dp
        borderColor.value = Color.LightGray
    }
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(60.dp)
            .background(noteColorValue(noteColorOption))
            .border(BorderStroke(circleThickness.value, borderColor.value), shape = CircleShape)
            .padding(circleThickness.value)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected.value)
            Icon(
                imageVector = Icons.Default.Check,
                tint = isCheckedTint,
                contentDescription = ""
            )
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