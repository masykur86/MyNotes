package com.example.core.repository

import com.example.core.data.Note

class NoteRepository(private val dataSource: NoteDataSource) {
    suspend fun addNote(note: Note) = dataSource.add(note)

    suspend fun getNote(id: Long) = dataSource.getNote(id)

    suspend fun getAll() = dataSource.getAll()

    suspend fun remove(note: Note) = dataSource.remove(note)
}