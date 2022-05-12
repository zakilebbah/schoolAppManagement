package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassRoom_Student_Dao {
    @Query("SELECT * FROM ClassRoom_Student")
    fun getAll(): List<ClassRoom_Student>
    @Query("SELECT * FROM ClassRoom_Student WHERE class_room_id==:class_room_id0 and student_id==:student_id0")
    fun loadCidSid(class_room_id0: Int,student_id0: Int ): ClassRoom_Student
    @Insert
    fun insert(classRoom_Student: ClassRoom_Student)
    @Delete
    fun delete(classRoom_Student: ClassRoom_Student)
    @Query("SELECT * FROM ClassRoom_Student where class_room_id == :id")
    fun getClassWithStudents(id: Int): LiveData<List<StudentWithclass>>
    @Update
    fun update(classRoom_Student: ClassRoom_Student)
    @Query("DELETE FROM ClassRoom_Student WHERE  class_room_id==:class_room_id0 and student_id==:student_id0")
    fun deleteByCid(class_room_id0: Int, student_id0: Int)
    @Query("DELETE FROM ClassRoom_Student WHERE  csid==:id")
    fun deleteById(id: Int)
    @Query("SELECT * FROM ClassRoom_Student where class_room_id == :id")
    fun getClassWithStudentsNumber(id: Int): List<StudentWithclass>
}