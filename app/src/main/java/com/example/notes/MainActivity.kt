package com.example.notes

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.databinding.DialogUpdateDeleteBinding
import java.text.SimpleDateFormat
import java.util.Locale

// Just start git and github
class MainActivity : AppCompatActivity() {

    // Late initialization of the binding, view model, and adapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating the layout and setting the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel for managing the data
        noteViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        // Set up RecyclerView with a LinearLayoutManager and the noteAdapter
        noteAdapter = NoteAdapter { note ->
            showUpdateDeleteDialog(note) // Show update/delete dialog on item click
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = noteAdapter

        // Observe LiveData for changes in notes
        noteViewModel.allNotes.observe(this) { notes ->
            notes?.let {
                noteAdapter.submitList(it) // Submit the list of notes to the adapter
            }
        }

        // Add Note Button Click Listener to navigate to AddNoteActivity
        binding.addNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to show a dialog for updating or deleting a note
    private fun showUpdateDeleteDialog(note: Note) {
        val dialogBinding = DialogUpdateDeleteBinding.inflate(layoutInflater) // Inflate dialog layout
        val builder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setTitle("Update/Delete Note")

        val dialog = builder.create()
        dialog.show() // Show the dialog

        // Populate the dialog with the current note's data
        dialogBinding.noteTitleEditText.setText(note.title)
        dialogBinding.noteContentEditText.setText(note.content)

        // Update Button Click Listener
        dialogBinding.updateButton.setOnClickListener {
            val updatedTitle = dialogBinding.noteTitleEditText.text.toString() // Get updated title
            val updatedContent = dialogBinding.noteContentEditText.text.toString() // Get updated content

            // Check if the fields are not empty
            if (updatedTitle.isBlank() || updatedContent.isBlank()) {
                Toast.makeText(this, "Title and Content cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Create an updated note and update it in the database
                val updatedNote = note.copy(
                    title = updatedTitle,
                    content = updatedContent,
                    timestamp = getCurrentTimestamp() // Set the current timestamp
                )
                noteViewModel.updateNote(updatedNote) // Update note in ViewModel
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Dismiss the dialog
            }
        }

        // Delete Button Click Listener
        dialogBinding.deleteButton.setOnClickListener {
            // Delete the note from the database
            noteViewModel.deleteNote(note)
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            dialog.dismiss() // Dismiss the dialog
        }
    }

    // Companion object to provide the current timestamp in a specific format
    companion object{
        fun getCurrentTimestamp(): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) // Format for date and month
            return sdf.format(System.currentTimeMillis()) // Get the current date in the specified format
        }
    }
}
