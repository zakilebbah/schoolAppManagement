package com.example.schoolapp

import android.app.Application
import com.example.schoolapp.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repositoryClasse by lazy { ClasseRepository(database.classeDao()) }
    val repositoryStudent by lazy { StudentRepository(database.studentDao()) }
    val repositoryClasseStudent by lazy { ClassRoom_Student_Repository(database.ClassRoomStudentDao()) }
    val repositoryAttendance by lazy { AttendanceRepository(database.AttendanceDao()) }
    val repositoryMatier by lazy { MatiereRepository(database.MatiereDAO()) }
    val repositoryExamen by lazy { ExamenRepository(database.ExamenDAO()) }
    val repositoryNote by lazy { NoteRepository(database.NoteDAO()) }
    val repositoryClasseMatiere by lazy { ClasseMatiereRepository(database.ClasseMatiereDao()) }
}