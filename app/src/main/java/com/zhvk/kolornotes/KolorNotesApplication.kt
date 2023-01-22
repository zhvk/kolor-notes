package com.zhvk.kolornotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KolorNotesApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
//    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
//    val database by lazy { NoteDatabase.getInstance(this, applicationScope) }
//    public val repository by lazy { NoteRepository(database.noteDao()) }
}