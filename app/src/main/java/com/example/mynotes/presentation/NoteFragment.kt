package com.example.mynotes.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.mynotes.R
import com.example.mynotes.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.item_note.*


class NoteFragment : Fragment() {
    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteid
        }
        if (noteId !=0L){
            viewModel.getNote(noteId)
        }
        fabSave.setOnClickListener {
            if (etTitle.text.toString() != "" || etContent.text.toString() != "") {
                val time: Long = System.currentTimeMillis()
                currentNote.title = etTitle.text.toString()
                currentNote.content = etContent.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)

            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }
        observeViewmodel()
    }
        private fun observeViewmodel(){
            viewModel.saved.observe(this, Observer {
                if (it){
                    Toast.makeText(context, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    Navigation.findNavController(etTitle).popBackStack()
                }else{
                    Toast.makeText(context, "ada kesalahan, coba lagi ges", Toast.LENGTH_SHORT).show()
                }

            })
            viewModel.currentNote.observe(this, Observer {
                note->note?.let {
                currentNote = it
                etTitle.setText(it.title,TextView.BufferType.EDITABLE)
                etContent.setText(it.content,TextView.BufferType.EDITABLE)
            }
            })
        }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etTitle.windowToken,0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote ->{
                if(context != null && noteId !=0L){
                    AlertDialog.Builder(context!!)
                        .setTitle("hapus catatan?")
                        .setMessage("yakin kah?")
                        .setPositiveButton("iye"){dialogInterface, i -> viewModel.deleteNote(currentNote) }
                        .setNegativeButton("bohongku"){dialogInterface, i ->  }
                        .create()
                        .show()
                }
            }
        }
        return true
    }
}
