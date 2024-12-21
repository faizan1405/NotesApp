package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel for managing the data related to notes
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    // Reference to the NoteDao for database operations
    private val noteDao: NoteDao = NoteDatabase.getDatabase(application).noteDao()

    // LiveData object to observe all notes in the database
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    // Function to insert a new note into the database
    fun insertNote(note: Note) {
        // Launch a coroutine to perform the database operation in the background
        viewModelScope.launch {
            noteDao.insert(note) // Insert the note into the database
        }
    }

    // Function to delete a specific note from the database
    fun deleteNote(note: Note) {
        // Launch a coroutine to perform the database operation in the background
        viewModelScope.launch {
            noteDao.delete(note) // Delete the note from the database
        }
    }

    // Function to update an existing note in the database
    fun updateNote(note: Note) {
        // Launch a coroutine to perform the database operation in the background
        viewModelScope.launch {
            noteDao.update(note) // Update the note in the database
        }
    }
}
