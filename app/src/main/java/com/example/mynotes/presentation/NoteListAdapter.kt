package com.example.mynotes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.mynotes.R
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NoteListAdapter(var notes :ArrayList<Note>,val action: ListAction): RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(){
    inner class NoteViewHolder(view:View):RecyclerView.ViewHolder(view){
         private val  layout = view.noteLayout
         private val  noteTitle = view.title
         private val  noteContent = view.content
         private val  noteDate = view.date

        fun bind(note:Note){
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "last updated :${sdf.format(resultDate)}"
            layout.setOnClickListener{
                action.onClick(note.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun getItemCount() = notes.size


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun updateNotes(newNotes:List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }


}