package com.zhvk.kolornotes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zhvk.kolornotes.core.Constants.Companion.NOTE_ID
import com.zhvk.kolornotes.navigation.Screen.MainScreen
import com.zhvk.kolornotes.navigation.Screen.NoteScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.route
    ) {
        composable(
            route = MainScreen.route
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = NoteScreen.route + "?$NOTE_ID={$NOTE_ID}",
            arguments = listOf(
                navArgument(NOTE_ID) {
                    type = NavType.IntType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            NoteScreen(
                noteId = backStackEntry.arguments?.getInt(NOTE_ID)
            )
        }
    }
}
