package com.example.schoolapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.schoolapp.data.*
import kotlinx.coroutines.launch

class AttendanceViewModel(private val repository: AttendanceRepository): ViewModel() {

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(attendance: Attendance) = viewModelScope.launch {
        repository.insert(attendance)
    }
    fun update(attendance: Attendance) = viewModelScope.launch {
        repository.update(attendance)
    }
    fun searchByDate(date: String, sid: Int, cid: Int):  Attendance {
        return repository.searchByDate(date, sid, cid)
    }
}
class AttendanceModelFactory(private val repository: AttendanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelAttendance: Class<T>): T {
        if (modelAttendance.isAssignableFrom(AttendanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AttendanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}