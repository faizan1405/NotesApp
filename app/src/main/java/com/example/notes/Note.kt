package com.example.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class representing a Note in the Room database
@Entity(tableName = "note_table") // Specifies the table name in the database
data class Note(
    @PrimaryKey(autoGenerate = true) // Auto-generates the primary key for each note
    val id: Int = 0, // Unique identifier for each note
    val title: String, // The title of the note
    val content: String, // The content of the note
    val timestamp: String // The timestamp for when the note was created or last updated
    // You can use a `Date` object or `Long` for the timestamp if needed (e.g., for better sorting or manipulation)
)
