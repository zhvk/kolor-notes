package com.zhvk.kolornotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zhvk.kolornotes.core.Constants.Companion.NOTE_ID
import com.zhvk.kolornotes.presentation.main_screen.MainScreen
import com.zhvk.kolornotes.presentation.note_screen.NoteScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.NoteScreen.route + "?$NOTE_ID={noteId}",
            arguments = listOf(
                navArgument(NOTE_ID) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            NoteScreen(
                navController = navController,
                noteId = backStackEntry.arguments?.getLong(NOTE_ID)
            )
        }
    }
}
