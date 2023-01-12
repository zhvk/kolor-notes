package com.zhvk.kolornotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                SearchCard({})
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
//                        .background(Color.LightGray)
                )
                TableCards(4)
            }
        }
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
                        text = "Start searching notes..."
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
fun TableCards(
    rowNumber: (Int),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        var i = 0
        while (i < rowNumber) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NoteCard(cardText = i.toString(), 0.5f)
                NoteCard(cardText = i.toString(), 1f)
            }
            i++
        }
    }
}

@Composable
fun NoteCard(
    cardText: (String),
    fillPercentage: (Float)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(fillPercentage)
            .height(250.dp)
            .padding(6.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        backgroundColor = randomElementFromGivenList()
    ) {
        Text(
            text = cardText,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview
fun NoteCardPreview() {
    NoteCard("Lorem ipsum", 1f)
}

@Composable
@Preview
fun SearchCardPreview() {
    SearchCard(onSearchClicked = {})
}

@Composable
@Preview
fun SimpleTextField() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it }
    )
}

@Composable
fun randomElementFromGivenList(): Color {
    val list =
        listOf(
            colorResource(id = R.color.pastel_blue),
            colorResource(id = R.color.pastel_red),
            colorResource(id = R.color.pastel_yellow),
            colorResource(id = R.color.pastel_green),
            colorResource(id = R.color.pastel_purple)
        )
    val randomIndex = Random.nextInt(list.size);
    return list[randomIndex]
}
