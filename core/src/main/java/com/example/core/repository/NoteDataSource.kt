package com.example.core.repository

import com.example.core.data.Note

interface NoteDataSource {
    suspend fun add(note: Note)

    suspend fun getNote(id: Long): Note?

    suspend fun getAll(): List<Note>

    suspend fun remove(note: Note)
}