package com.example.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room Database class for managing the 'note_table' and providing access to the NoteDao
@Database(entities = [Note::class], version = 1) // Specify entities (Note) and the database version
abstract class NoteDatabase : RoomDatabase() {

    // Abstract function that provides access to the NoteDao
    abstract fun noteDao(): NoteDao

    companion object {
        // Volatile ensures that the INSTANCE is always read from main memory and is thread-safe
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        // Singleton pattern to ensure only one instance of the database is created
        fun getDatabase(context: Context): NoteDatabase {
            // Check if the instance is already created
            return INSTANCE ?: synchronized(this) { // Ensure thread-safety for concurrent access
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use application context to avoid memory leaks
                    NoteDatabase::class.java, // Class of the database (NoteDatabase)
                    "note_database" // Name of the database file
                ).build() // Build the database instance
                INSTANCE = instance // Save the instance for future use
                instance // Return the created instance
            }
        }
    }
}
