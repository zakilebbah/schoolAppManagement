package com.example.schoolapp.data




import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM Student")
    fun getAll(): Flow<List<Student>>

    @Query("SELECT * FROM Student WHERE sid ==:sid")
    fun loadById(sid: Int): Student
    @Query("DELETE FROM Student WHERE sid ==:sid")
    fun deleteById(sid: Int)
    @Query("SELECT * FROM Student WHERE nom LIKE :name0 OR prenom LIKE :name0")
    fun findName(name0: String): LiveData<List<Student>>

    @Insert
    fun insertStudent(student: Student): Long
    @Update(entity = Student::class)
    fun update(student: Student)
    @Delete
    fun delete(student: Student)

    @Query("DELETE FROM Student")
    fun deleteAll()
}