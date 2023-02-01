package com.zhvk.kolornotes.presentation.main_screen

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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.core.Constants.Companion.NOTE_ID
import com.zhvk.kolornotes.domain.model.Note
import com.zhvk.kolornotes.utils.getNotesComposable
import com.zhvk.kolornotes.navigation.Screen
import com.zhvk.kolornotes.utils.noteColorValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        QuickActionBarMainScreen(navController, notesViewModel)
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
                        text = stringResource(id = R.string.search_hint)
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
                navController.navigate(Screen.NoteScreen.route + "?$NOTE_ID=${it}")
            })
        }
    }
}

@Composable
fun NoteCardItem(
    note: (Note),
    onClicked: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(6.dp)
            .clickable(onClick = { onClicked(note.id) }),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = noteColorValue(note.backgroundColor)
    ) {
        Box(    // TODO: Remove this box later it's just for showing ID in the bottom right corner
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = note.noteTitle!!,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = note.noteBody!!)
            }
            Text(
                modifier = Modifier.padding(12.dp),
                text = note.id.toString(),
                fontWeight = FontWeight.ExtraLight,
                fontSize = 10.sp
            )
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
fun QuickActionBarMainScreen(
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    val scope = rememberCoroutineScope()

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
            scope.launch {
                val newId: Long

                // Coroutine Scope, once you launch it, has its own "thread" it is working on. That is
                // Dispatchers.Main in this case. We need to use withContext to tell the Coroutine to
                // switch to a new "thread", Dispatchers.IO because database Insert function can't be
                // run on the main thread. That's why only this part of the code must be run on the IO
                // (Input/Output, for disc and network operations
                withContext(Dispatchers.IO) {
                    newId = notesViewModel.addBlankNote()
                }

                navController.navigate(Screen.NoteScreen.route + "?$NOTE_ID=${newId}")
            }
        }) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
        }
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    }
}

// TODO: Populate Database only if DB is empty
@Composable
fun PopulateDatabase(
    notesViewModel: NotesViewModel
) {
    // Delete all content here.
//    notesViewModel.deleteAllNotes()

    // Add sample notes.
    for (note in getNotesComposable()) {
        notesViewModel.addNote(note)
    }
}
