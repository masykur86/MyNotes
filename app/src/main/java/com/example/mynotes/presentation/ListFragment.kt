package com.example.mynotes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.framework.ListViewModel
import com.example.mynotes.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(),ListAction {

    private val notesListAdapter = NoteListAdapter(arrayListOf(),this)
    private lateinit var viewModel: ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesListVIew.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }
        addNote.setOnClickListener {
            goToNoteDetails()
        }
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.notes.observe(this, Observer { noteList ->
            loadingVIew.visibility = View.GONE
            notesListVIew.visibility =View.VISIBLE
            notesListAdapter.updateNotes(noteList.sortedByDescending { it.updateTime })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }
    private fun  goToNoteDetails(id :Long = 0L){
        val action : NavDirections = ListFragmentDirections.actiongotonote(id)
        Navigation.findNavController(notesListVIew).navigate(action)
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }


}