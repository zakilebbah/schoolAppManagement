package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MatiereDAO {
    @Query("SELECT * FROM Matiere")
    fun getAll(): Flow<List<Matiere>>

    @Query("SELECT * FROM Matiere WHERE mid ==:mid")
    fun loadById(mid: Int): Matiere

    @Query("SELECT * FROM Matiere WHERE nom LIKE :name0")
    fun findName(name0: String): LiveData<List<Matiere>>

    @Insert
    fun insert(matiere: Matiere)
    @Update(entity = Matiere::class)
    fun update(matiere: Matiere)
    @Delete
    fun delete(matiere: Matiere)

    @Query("DELETE FROM Matiere")
    fun deleteAll()
}