package com.zhvk.kolornotes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.navigation.Screen
import com.zhvk.kolornotes.presentation.main_screen.NotesViewModel

@Composable
fun MainScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    populateDatabase(notesViewModel)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
        ) {
            SearchCard({})
            NoteCardList(notesViewModel)
        }
        QuickActionBarMainScreen(navController)
    }
}

@Composable
fun SearchCard(
    onSearchClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .padding(20.dp),
        elevation = 20.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        val textState = remember { mutableStateOf(TextFieldValue()) }
        TextField(
            modifier = Modifier
                .fillMaxSize(),
            value = textState.value,
            onValueChange = { textState.value = it },
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Start searching notes..."
                    )
                }
            },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                IconButton(onClick = {/* TODO */ }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(textState.value.toString()) }
            ),
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = colorResource(id = R.color.mint_cream),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun NoteCardList(
//    notes: List<Note>,
    noteViewModel: NotesViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 0.dp, 6.dp, 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        items(noteViewModel.allNotes) { note ->
            NoteCardItem(note = note, onClicked = { Log.d("MainScreen.kt", note.id.toString()) })
        }
    }
}

@Composable
fun NoteCardItem(
    note: (Note),
    onClicked: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(6.dp)
            .clickable(onClick = { onClicked(note.id!!) }),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.alice_blue)
//        backgroundColor = note.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(text = note.noteTitle!!, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = note.noteText!!)
        }
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

@Composable
fun QuickActionBarMainScreen(navController: NavController) {
    val noteTitle = stringResource(id = R.string.batman_ipsum)
    val noteText = stringResource(id = R.string.batman_ipsum_2)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colorResource(id = R.color.mint_cream)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {
            navController.navigate(Screen.NoteScreen.route + "?noteTitle=$noteTitle&noteText=$noteText")
        }) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
        }
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    }
}

@Composable
fun populateDatabase(
    notesViewModel: NotesViewModel
) {
    // Delete all content here.
    notesViewModel.deleteAllNotes()

    // Add sample notes.
    for (note in getNotesComposable()) {
        notesViewModel.addNote(note)
    }
}
