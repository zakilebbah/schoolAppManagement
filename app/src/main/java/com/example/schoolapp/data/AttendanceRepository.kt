package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class AttendanceRepository(private val attendanceDao: AttendanceDao) {
    @WorkerThread
    fun getAttendance(aid: Int): LiveData<Attendance> = attendanceDao.loadById(aid)
    val allClasses: Flow<List<Attendance>> = attendanceDao.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(attendance: Attendance) {
        attendanceDao.insert(attendance)
    }
    @WorkerThread
    suspend fun update(attendance: Attendance) {
        attendanceDao.update(attendance)
    }
    @WorkerThread
    fun updateClasse(attendance: Attendance) {
        attendanceDao.update(attendance)
    }
    @WorkerThread
    fun deleteClasse(aid: Int) {
        attendanceDao.deleteById(aid)
    }
    @WorkerThread
    fun searchByDate(date: String, sid: Int, cid: Int): Attendance {
        return attendanceDao.searchByDate(date, sid, cid)
    }
}