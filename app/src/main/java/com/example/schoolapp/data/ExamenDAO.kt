package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ExamenDAO {
    @Query("SELECT * FROM Examen")
    fun getAll(): Flow<List<Examen>>

    @Query("SELECT * FROM Examen WHERE Eid ==:eid")
    fun loadById(eid: Int): Examen

    @Query("SELECT * FROM Examen WHERE nom LIKE :name0")
    fun findName(name0: String): LiveData<List<Examen>>

    @Insert
    fun insert(examen: Examen)
    @Update(entity = Examen::class)
    fun update(examen: Examen)
    @Delete
    fun delete(examen: Examen)

    @Query("DELETE FROM Examen")
    fun deleteAll()
}