package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Classe")
class Classe(
    @PrimaryKey(autoGenerate = true) val cid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "grade") val grade: String?,
    @ColumnInfo(name = "date") val date: String?,

    ) {

}