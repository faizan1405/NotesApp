package com.example.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// NoteAdapter class that binds data to RecyclerView items
class NoteAdapter(
    private val onNoteClick: (Note) -> Unit // Lambda for handling item clicks
    ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // List to store notes
    private var noteList: List<Note> = emptyList()

    // ViewHolder class for binding views to RecyclerView item
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // References to the views inside the note_item layout
        val noteTitle: TextView = itemView.findViewById(R.id.title)
        val noteContent: TextView = itemView.findViewById(R.id.content)
        val noteTimestamp: TextView = itemView.findViewById(R.id.timing)
    }

    // Function to submit a new list of notes and refresh the RecyclerView
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(notes: List<Note>) {
        noteList = notes // Assign the new list of notes
        notifyDataSetChanged() // Notify adapter that the data set has changed
    }

    // Creates and returns a ViewHolder to hold a single note item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the note_item layout
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView) // Return a new ViewHolder
    }

    // Binds the data (note) to the views of the ViewHolder
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position] // Get the note at the given position
        // Set the note's title, content, and timestamp to the corresponding views
        holder.noteTitle.text = note.title
        holder.noteContent.text = note.content
        holder.noteTimestamp.text = note.timestamp

        // Set the click listener on the item view to trigger the onNoteClick lambda
        holder.itemView.setOnClickListener {
            onNoteClick(note) // Call the lambda function with the clicked note
        }
    }

    // Return the total number of notes in the list
    override fun getItemCount(): Int = noteList.size
}
