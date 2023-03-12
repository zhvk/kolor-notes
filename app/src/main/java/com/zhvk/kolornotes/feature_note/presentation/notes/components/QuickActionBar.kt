package com.zhvk.kolornotes.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.core.Constants
import com.zhvk.kolornotes.core.Screen
import com.zhvk.kolornotes.feature_note.presentation.notes.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun QuickActionBar(
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

                navController.navigate(Screen.NoteScreen.route + "?${Constants.NOTE_ID}=${newId}")
            }
        }) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
        }
        IconButton(modifier = Modifier.padding(4.dp, 0.dp), onClick = {/* TODO */ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    }
}