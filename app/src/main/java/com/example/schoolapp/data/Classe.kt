package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Classe")
class Classe(
    @PrimaryKey(autoGenerate = true) var cid: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "grade") var grade: String?,
    @ColumnInfo(name = "date") var date: String?,
    @Ignore var nbr: Int?,
    ) {
    constructor():this(0,"", "", "", null)
}