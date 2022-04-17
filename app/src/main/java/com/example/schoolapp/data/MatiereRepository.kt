package com.example.schoolapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class MatiereRepository(private val matiereDAO: MatiereDAO) {
    @WorkerThread
    fun getMatiere(mid: Int) = matiereDAO.loadById(mid)
    val allMatiers: Flow<List<Matiere>> = matiereDAO.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(matiere: Matiere) {
        matiereDAO.insert(matiere)
    }
    @WorkerThread
    fun update(matiere: Matiere) {
        matiereDAO.update(matiere)
    }
    @WorkerThread
    fun search(name: String): LiveData<List<Matiere>> {
        return matiereDAO.findName(name)
    }
}