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

    @Query("DELETE FROM ClasseMatiere WHERE  id_classe==:class_id0 and id_matiere==:matiere_id")
    fun deleteByCidMid(class_id0: Int,matiere_id: Int)
    @Query("SELECT * FROM ClasseMatiere WHERE id_classe==:class_id0 and id_matiere==:matiere_id0")
    fun loadCidMid1(class_id0: Int,matiere_id0: Int ): ClasseMatiere
}
