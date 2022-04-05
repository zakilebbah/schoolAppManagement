package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.ClasseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ClasseViewModel(private val repository: ClasseRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Classe>> = repository.allClasses.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(classe: Classe) = viewModelScope.launch {
        repository.insert(classe)
    }
    fun update(classe: Classe) = viewModelScope.launch {
        repository.updateClasse(classe)
    }

    fun classById(cid : Int): LiveData<Classe> {
        return repository.getClasse(cid)
    }
    fun deleteById(cid : Int){
        return repository.deleteClasse(cid)
    }

}

class WordViewModelFactory(private val repository: ClasseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClasseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClasseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}