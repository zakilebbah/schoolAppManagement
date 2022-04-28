package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.*
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
    fun searchNote(sid: Int, eid: Int, cid: Int): Note {
        return repository.search(sid, eid, cid)
    }
    fun searchNoteByStudent(sid: Int): LiveData<List<Note>> {
        return repository.searchByStudent(sid)
    }
    fun searchNoteByStudentList(sid: Int): List<Note> {
        return repository.searchByStudentList(sid)
    }

}
class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelExamen: Class<T>): T {
        if (modelExamen.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}