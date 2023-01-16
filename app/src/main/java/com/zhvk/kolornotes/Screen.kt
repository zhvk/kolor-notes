package com.zhvk.kolornotes

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object NoteScreen : Screen("note_screen")
}
