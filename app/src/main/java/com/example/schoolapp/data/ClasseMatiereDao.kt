package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClasseMatiereDao {
    @Query("SELECT * FROM ClasseMatiere")
    fun getAll(): Flow<List<ClasseMatiere>>

    @Query("SELECT * FROM ClasseMatiere WHERE CMid ==:cmid")
    fun loadById(cmid: Int): ClasseMatiere

    @Insert
    fun insert(classeMatiere: ClasseMatiere)
    @Update(entity = ClasseMatiere::class)
    fun update(classeMatiere: ClasseMatiere)
    @Delete
    fun delete(classeMatiere: ClasseMatiere)

    @Query("DELETE FROM ClasseMatiere")
    fun deleteAll()
}