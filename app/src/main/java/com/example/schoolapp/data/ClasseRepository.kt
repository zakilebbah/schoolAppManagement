package com.example.schoolapp.data


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [PlantDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
class ClasseRepository(private val classeDao: ClasseDao) {

    @WorkerThread
    fun getClasse(cid: Int): LiveData<Classe> = classeDao.loadById(cid)
    val allClasses: Flow<List<Classe>> = classeDao.getAll()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(classe: Classe) {
        classeDao.insertClass(classe)
    }
    @WorkerThread
    fun updateClasse(classe: Classe) {
        classeDao.update(classe)
    }
    @WorkerThread
    fun deleteClasse(cid: Int) {
        classeDao.deleteById(cid)
    }
//    fun getPlant(plantId: String) = plantDao.getPlant(plantId)
//
//    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
//        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}
