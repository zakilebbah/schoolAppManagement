package com.example.schoolapp.ui.home

import androidx.lifecycle.*
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.ClasseRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ClasseRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    val allWords: LiveData<List<Classe>> = repository.allClasses.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(classe: Classe) = viewModelScope.launch {
        repository.insert(classe)
    }
}