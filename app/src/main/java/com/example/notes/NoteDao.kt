// NoteDao.kt
package com.example.notes

import androidx.lifecycle.LiveData
import androidx.room.*

// DAO (Data Access Object) interface for interacting with the "note_table" in the Room database
@Dao
interface NoteDao {

    // Query to retrieve all notes, ordered by their 'id' in descending order (latest first)
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>> // Returns a LiveData object, so the UI can observe changes

    // Insert a new note into the database
    @Insert
    suspend fun insert(note: Note) // 'suspend' ensures this function runs in a background thread

    // Delete an existing note from the database
    @Delete
    suspend fun delete(note: Note) // 'suspend' ensures this function runs in a background thread

    // Update an existing note in the database
    @Update
    suspend fun update(note: Note) // 'suspend' ensures this function runs in a background thread
}
