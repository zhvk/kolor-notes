package com.zhvk.kolornotes.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zhvk.kolornotes.feature_note.presentation.navigation.Navigation
import com.zhvk.kolornotes.ui.theme.KolorNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KolorNotesTheme() {
                val navHostController = rememberNavController()
                Navigation(navHostController)
            }
        }

    }

}
