package com.zhvk.kolornotes.feature_note.presentation.note.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.presentation.note.NoteViewModel

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