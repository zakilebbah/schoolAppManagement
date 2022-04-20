package com.example.schoolapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.schoolapp.data.*
import kotlinx.coroutines.launch

class ClasseMatiereViewModel(private val repository: ClasseMatiereRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
//    val allStudents: LiveData<List<ClassRoom_Student>> = repository.getClassWithStudents(-1).asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(classMatiere: ClasseMatiere) = viewModelScope.launch {
        repository.insert(classMatiere)
    }
    fun update(classMatiere: ClasseMatiere) = viewModelScope.launch {
        repository.update(classMatiere)
    }
    fun deleteByCidMid(class_id0: Int,matiere_id0: Int) = viewModelScope.launch {
        repository.deleteByCidMid(class_id0, matiere_id0)
    }
    fun loadCidMid(cid: Int, mid: Int):ClasseMatiere  {
        return repository.loadCidMid(cid, mid)
    }


}



//    fun classById(cid : Int): Classe {
//        return repository.getClasse(cid)
//    }


class ClasseMatiereModelFactory(private val repository: ClasseMatiereRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClassMatiere: Class<T>): T {
        if (modelClassMatiere.isAssignableFrom(ClasseMatiereViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClasseMatiereViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}