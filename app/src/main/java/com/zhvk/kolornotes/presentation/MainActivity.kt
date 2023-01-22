package com.zhvk.kolornotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zhvk.kolornotes.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            Navigation(navHostController)

            /*val coroutineScope = CoroutineScope(Dispatchers.Main)
            val database by lazy { NoteDatabase.getInstance(this, coroutineScope)}
            val repository by lazy { NoteRepository(database.noteDao()) }*/
        }

    }

}
