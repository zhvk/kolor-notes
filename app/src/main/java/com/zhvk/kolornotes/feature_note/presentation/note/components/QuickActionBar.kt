package com.zhvk.kolornotes.feature_note.presentation.note.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuickActionBar(
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