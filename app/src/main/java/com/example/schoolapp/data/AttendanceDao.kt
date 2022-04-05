package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao

interface AttendanceDao {
    @Query("SELECT * FROM Attendance")
    fun getAll(): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance WHERE aid ==:aid")
    fun loadById(aid: Int): LiveData<Attendance>

    @Insert
    fun insert(attendance: Attendance)
    @Update
    fun update(attendance: Attendance)
    @Delete
    fun delete(attendance: Attendance)

    @Query("DELETE FROM Attendance")
    fun deleteAll()
    @Query("DELETE FROM Attendance WHERE aid ==:aid")
    fun deleteById(aid: Int)
    @Query("SELECT * FROM Attendance WHERE date ==:date AND student_id==:sid AND classe_id==:cid")
    fun searchByDate(date: String, sid: Int, cid: Int): Attendance
}