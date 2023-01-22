package com.zhvk.kolornotes

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun NoteScreen(
    noteId: Int?
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            Note(noteId)
        }
        QuickActionBarNoteScreen()
    }
}

@Composable
fun Note(
    noteId: Int?,
//    onTitleChange: () -> Unit
) {
    val noteTitle = "Note Title"
    val noteText = "Note Text"

    // Toolbar
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            // TODO OnBackPressed
//            navController.popBackStack()
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
    val noteTitleState = remember { mutableStateOf(noteTitle) }
    val noteTextState = remember { mutableStateOf(noteText) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            value = noteTitleState.value,
            onValueChange = { noteTitleState.value = it },
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        val n = 0
        ClickCounter(n, onClick = {
            //n = 1
        })

        SharedPrefsToggle("Hi", true, {})

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = noteTextState.value,
            onValueChange = { noteTextState.value = it },
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

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}

@Composable
fun SharedPrefsToggle(
    text: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit
) {
    Row {
        Text(text)
        Checkbox(checked = value, onCheckedChange = onValueChanged)
    }
}