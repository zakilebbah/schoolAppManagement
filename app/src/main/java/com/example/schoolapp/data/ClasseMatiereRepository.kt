package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ClasseMatiereRepository(private val classeMatiereDao: ClasseMatiereDao){
    @WorkerThread
    fun getStudent(cmid: Int) = classeMatiereDao.loadById(cmid)
    val allStudents: Flow<List<ClasseMatiere>> = classeMatiereDao.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(classeMatiere: ClasseMatiere) {
        classeMatiereDao.insert(classeMatiere)
    }
    @WorkerThread
    fun update(classeMatiere: ClasseMatiere) {
        classeMatiereDao.update(classeMatiere)
    }
}