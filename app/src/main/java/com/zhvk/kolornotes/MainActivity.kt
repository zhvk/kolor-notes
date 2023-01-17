package com.zhvk.kolornotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.zhvk.kolornotes.database.AppDatabase
import com.zhvk.kolornotes.entity.Note

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }

        // If your app runs in a single process, you should follow the singleton design
        //  pattern when instantiating an AppDatabase object. Each RoomDatabase instance is fairly
        //  expensive, and you rarely need access to multiple instances within a single process.
        //  If your app runs in multiple processes, include enableMultiInstanceInvalidation() in
        //  your database builder invocation. That way, when you have an instance of AppDatabase in
        //  each process, you can invalidate the shared database file in one process, and this
        //  invalidation automatically propagates to the instances of AppDatabase within other
        //  processes.
        // TODO Singleton
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "notes"
        ).build()

        // TODO This causes crash: Cannot access database on the main thread since it may
        //  potentially lock the UI for a long period of time.
//        val noteDao = db.noteDao()
//        val notes: List<Note> = noteDao.getAll()
    }
}
