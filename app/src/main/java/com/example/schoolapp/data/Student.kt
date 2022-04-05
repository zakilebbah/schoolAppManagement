package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
class Student(
    @PrimaryKey(autoGenerate = true) var sid: Int = 0,
    @ColumnInfo(name = "nom") val name: String,
    @ColumnInfo(name = "prenom") val prenom: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "tel") val tel: String,
    @ColumnInfo(name = "adresse") val adresse: String,
    @ColumnInfo(name = "DateNaissance") val DateNaissance: String?,
    @ColumnInfo(name = "date_creation") val date_creation: String?,

    ) {

}