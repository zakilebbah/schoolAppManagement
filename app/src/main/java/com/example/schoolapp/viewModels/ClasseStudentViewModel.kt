package com.example.schoolapp.viewModels

import androidx.lifecycle.*
import com.example.schoolapp.data.*
import kotlinx.coroutines.launch

class ClasseStudentViewModel(private val repository: ClassRoom_Student_Repository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
//    val allStudents: LiveData<List<ClassRoom_Student>> = repository.getClassWithStudents(-1).asLiveData()
    fun allStudents(id: Int): LiveData<List<StudentWithclass>> {
       return  repository.getClassWithStudents(id)
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(classe: ClassRoom_Student) = viewModelScope.launch {
        repository.insert(classe)
    }
//    fun update(classe: ClassRoom_Student) = viewModelScope.launch {
//        repository.updateClasse(classe)
//    }

//    fun classById(cid : Int): Classe {
//        return repository.getClasse(cid)
//    }
}

class ClasseStudentModelFactory(private val repository: ClassRoom_Student_Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClassStudent: Class<T>): T {
        if (modelClassStudent.isAssignableFrom(ClasseStudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClasseStudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}