package com.zhvk.kolornotes.core

import com.zhvk.kolornotes.core.Constants.Companion.MAIN_SCREEN
import com.zhvk.kolornotes.core.Constants.Companion.NOTE_SCREEN

sealed class Screen(val route: String) {
    object MainScreen : Screen(MAIN_SCREEN)
    object NoteScreen : Screen(NOTE_SCREEN)
}
