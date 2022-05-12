package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.ClasseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ClasseViewModel(private val repository: ClasseRepository) : ViewModel() {
    val allWords: LiveData<List<Classe>> = repository.allClasses.asLiveData()
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

class ClasseViewModelFactory(private val repository: ClasseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClasseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClasseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}