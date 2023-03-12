package com.zhvk.kolornotes.feature_note.presentation.notes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhvk.kolornotes.feature_note.domain.model.Note
import com.zhvk.kolornotes.utils.noteColorValue

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