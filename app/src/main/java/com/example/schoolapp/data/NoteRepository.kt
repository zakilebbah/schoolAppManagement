package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDAO) {
    @WorkerThread
    fun getNote(nid: Int) = noteDAO.loadById(nid)
    val allNotes: Flow<List<Note>> = noteDAO.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }
    @WorkerThread
    fun search(sid: Int, eid: Int, cid: Int): Note {
        return noteDAO.search(sid, eid, cid)
    }
    @WorkerThread
    fun searchByStudent(sid: Int):  LiveData<List<Note>> {
        return noteDAO.searchByStudent(sid)
    }
    @WorkerThread
    fun update(note: Note) {
        noteDAO.update(note)
    }
}