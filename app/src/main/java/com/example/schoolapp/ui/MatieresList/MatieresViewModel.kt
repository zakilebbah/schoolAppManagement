package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MatieresViewModel(private val repository: MatiereRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMatiers: LiveData<List<Matiere>> = repository.allMatiers.asLiveData()

    fun loadById(mid : Int): Matiere {
        return repository.getMatiere(mid)
    }
    fun insert(matiere: Matiere) = viewModelScope.launch {
        repository.insert(matiere)
    }
    fun update(matiere: Matiere) = viewModelScope.launch {
        repository.update(matiere)
    }

}

class MatiereViewModelFactory(private val repository: MatiereRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatieresViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatieresViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}