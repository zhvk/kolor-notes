package com.zhvk.kolornotes.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.zhvk.kolornotes.feature_note.domain.model.Note

@Composable
fun NoteCardItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    cutCornerSize: Dp = 30.dp,
    onClicked: (Long) -> Unit
) {
    Box(
        modifier = modifier
            .height(250.dp)
            .padding(6.dp)
            .clickable(onClick = { onClicked(note.id) }),
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(note.color, 0x000000, 0.1f)),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
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