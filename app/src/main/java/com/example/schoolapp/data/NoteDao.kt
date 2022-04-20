package com.example.schoolapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE Nid ==:nid")
    fun loadById(nid: Int): Note

    @Insert
    fun insert(note: Note)
    @Update(entity = Note::class)
    fun update(note: Note)
    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAll()
}