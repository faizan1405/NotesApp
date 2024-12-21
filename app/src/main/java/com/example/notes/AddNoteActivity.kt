package com.example.notes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class AddNoteActivity : AppCompatActivity() {

    // Declare views
    lateinit var noteTitleEditText: EditText
    lateinit var noteContentEditText: EditText
    lateinit var saveNoteButton: Button
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notes)

        // Initialize views
        noteTitleEditText = findViewById(R.id.noteTitleEditText)
        noteContentEditText = findViewById(R.id.noteContentEditText)
        saveNoteButton = findViewById(R.id.saveNoteButton)

        // Initialize ViewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Save note when Save button is clicked
        saveNoteButton.setOnClickListener {
            val title = noteTitleEditText.text.toString()
            val content = noteContentEditText.text.toString()


            if (title.isBlank() || content.isBlank()) {
                Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Save the note using ViewModel
                val newNote = Note(
                    title = title,
                    content = content,
                    timestamp = MainActivity.getCurrentTimestamp() // Add timestamp logic here
                )
                noteViewModel.insertNote(newNote)

                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
                finish() // Go back to previous screen
            }
        }
    }
}
