package com.example.schoolapp.data

import androidx.annotation.WorkerThread
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
}