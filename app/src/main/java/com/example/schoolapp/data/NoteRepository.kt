package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {
    @WorkerThread
    fun getStudent(nid: Int) = noteDAO.loadById(nid)
    val allStudents: Flow<List<Note>> = noteDAO.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }
    @WorkerThread
    fun update(note: Note) {
        noteDAO.update(note)
    }
}