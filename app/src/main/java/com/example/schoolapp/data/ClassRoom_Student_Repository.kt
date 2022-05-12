package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

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
    @WorkerThread
    suspend fun update(classRoom_Student: ClassRoom_Student) {
        StudentClasseDao.update(classRoom_Student)
    }
    @WorkerThread
    suspend fun deleteByCidSid(class_room_id0: Int,student_id0: Int) {
        StudentClasseDao.deleteByCid(class_room_id0, student_id0)
    }
    @WorkerThread
    fun loadCidSid(cid: Int, sid: Int):ClassRoom_Student  {
        return StudentClasseDao.loadCidSid(cid, sid)
    }
    @WorkerThread
    fun getClassWithStudentsNumber(cid: Int):Int  {
        println(cid)
        return StudentClasseDao.getClassWithStudentsNumber(cid).size
    }


//    @WorkerThread
//    fun updateStudent(student: Student) {
//        StudentClasseDao.update(student)
//    }
}