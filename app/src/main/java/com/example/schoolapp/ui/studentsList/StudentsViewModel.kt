package com.example.schoolapp.ui.studentsList

import androidx.lifecycle.*
import com.example.schoolapp.data.Student
import com.example.schoolapp.data.StudentRepository
import kotlinx.coroutines.launch

class StudentsViewModel(private val repository: StudentRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Student>> = repository.allStudents.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(student: Student) = viewModelScope.launch {
        repository.insert(student)
    }
    fun insertClassePage(student: Student) :Long {
        return repository.insertClassePage(student)
    }
    fun studentById(sid : Int): Student {
        return repository.getStudent(sid)
    }
    fun update(student: Student) = viewModelScope.launch {
        repository.updateStudent(student)
    }
    fun delete(sid: Int) = viewModelScope.launch {
        repository.delete(sid)
    }
    fun search(name: String): LiveData<List<Student>> {
        return repository.searchStudent(name)
    }

}
class StudentsViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}