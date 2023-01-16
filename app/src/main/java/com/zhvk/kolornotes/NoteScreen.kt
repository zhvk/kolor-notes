package com.zhvk.kolornotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun NoteScreen(colorResource: Color) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource)
    ) {
        NoteScreenToolbar()
        Note()
    }
}

@Composable
fun NoteScreenToolbar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            // TODO OnBackPressed?
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
        IconButton(onClick = {
            // TODO Save
        }) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "")
        }
    }
}

@Composable
fun Note() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val titleState = remember { mutableStateOf(TextFieldValue()) }
        val noteState = remember { mutableStateOf(TextFieldValue()) }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            value = "Note Title", // TODO: textState
            onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
//                textColor = Color.Gray,
//                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = "Type note here...", // TODO: noteState
            onValueChange = {},
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