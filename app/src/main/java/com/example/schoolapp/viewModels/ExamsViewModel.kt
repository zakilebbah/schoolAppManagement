package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.ClasseRepository
import com.example.schoolapp.data.Examen
import com.example.schoolapp.data.ExamenRepository
import kotlinx.coroutines.launch

class ExamsViewModel(private val repository: ExamenRepository) : ViewModel() {

    val allWords: LiveData<List<Examen>> = repository.allExams.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(examen: Examen) = viewModelScope.launch {
        repository.insert(examen)
    }
    fun update(examen: Examen) = viewModelScope.launch {
        repository.update(examen)
    }

    fun examById(mid : Int): Examen {
        return repository.getExam(mid)
    }
}
class ExamsViewModelFactory(private val repository: ExamenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelExamen: Class<T>): T {
        if (modelExamen.isAssignableFrom(ExamsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExamsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}