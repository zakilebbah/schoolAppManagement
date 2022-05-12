package com.example.schoolapp.data


import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClasseDao {
    @Query("SELECT * FROM Classe")
    fun getAll(): Flow<List<Classe>>
    @Query("SELECT * FROM Classe WHERE cid ==:cid")
    fun loadById(cid: Int): LiveData<Classe>
    @Query("SELECT * FROM Classe WHERE name LIKE :name0")
    fun findName(name0: String): Classe
    @Insert
    fun insertClass(classe: Classe)
    @Update
    fun update(classe: Classe)
    @Delete
    fun delete(classe: Classe)
    @Query("DELETE FROM Classe")
    fun deleteAll()
    @Query("DELETE FROM Classe WHERE cid ==:cid")
    fun deleteById(cid: Int)
}