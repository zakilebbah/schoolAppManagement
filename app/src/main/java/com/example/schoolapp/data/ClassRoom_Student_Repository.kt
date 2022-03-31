package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ClassRoom_Student_Repository(private val StudentClasseDao: ClassRoom_Student_Dao) {
    @WorkerThread
    fun getClassWithStudents(cid: Int): LiveData<List<StudentWithclass>> {
        return StudentClasseDao.getClassWithStudents(cid)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(classRoom_Student: ClassRoom_Student) {
        StudentClasseDao.insert(classRoom_Student)
    }


//    @WorkerThread
//    fun updateStudent(student: Student) {
//        StudentClasseDao.update(student)
//    }
}