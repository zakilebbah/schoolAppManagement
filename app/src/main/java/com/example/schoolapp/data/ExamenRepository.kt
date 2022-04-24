package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ExamenRepository(private val examenDAO: ExamenDAO){
    @WorkerThread
    fun getExam(mid: Int) = examenDAO.loadById(mid)
    val allExams: Flow<List<Examen>> = examenDAO.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(examen: Examen) {
        examenDAO.insert(examen)
    }
    @WorkerThread
    fun update(examen: Examen) {
        examenDAO.update(examen)
    }
    @WorkerThread
    fun search(name: String): LiveData<List<Examen>> {
        return examenDAO.findName(name)
    }
    @WorkerThread
    fun loadByMatiere(mid: Int): LiveData<List<Examen>> {
        return examenDAO.loadByMatiere(mid)
    }

}