package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class StudentRepository (private val StudentDao: StudentDao){
    @WorkerThread
    fun getStudent(sid: Int) = StudentDao.loadById(sid)
    val allStudents: Flow<List<Student>> = StudentDao.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(student: Student) {
        StudentDao.insertStudent(student)
    }
     fun insertClassePage(student: Student): Long {
        return StudentDao.insertStudent(student)
    }
    @WorkerThread
    fun updateStudent(student: Student) {
        StudentDao.update(student)
    }
    @WorkerThread
    fun searchStudent(name: String): LiveData<List<Student>> {
        return StudentDao.findName(name)
    }
}