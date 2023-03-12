package com.zhvk.kolornotes.feature_note.presentation.note.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.zhvk.kolornotes.R
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.feature_note.presentation.note.NoteViewModel

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

            for (noteColorOption in Note.noteColors) {
                item {
                    Box(
                        modifier = Modifier.selectable(
                            selected = (noteState.color == noteColorOption.toArgb()),
                            onClick = {
                                noteState.color = noteColorOption.toArgb()
                                noteViewModel.updateNote(noteState)
                                Log.d("TAG", "UPDATE NOTE")
                            },
                            role = Role.RadioButton
                        )
                    ) {
                        RoundedCheckView(
                            selectedItem = Color(noteState.color),
                            noteColorOption = noteColorOption
                        )
                    }
                }
            }
        }
    }

}