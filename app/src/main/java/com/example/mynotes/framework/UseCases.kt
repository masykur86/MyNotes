package com.example.mynotes.framework

import com.example.core.usecase.AddNote
import com.example.core.usecase.GetAllNotes
import com.example.core.usecase.GetNote
import com.example.core.usecase.RemoveNotes

data class UseCases(
    val addNote:AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNotes:  RemoveNotes
)