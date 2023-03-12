package com.zhvk.kolornotes.feature_note.presentation.note.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.zhvk.kolornotes.R

@Composable
fun RoundedCheckView(
    selectedItem: Color,
    noteColorOption: Color,
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
            .background(noteColorOption)
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