package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
class Student(
    @PrimaryKey(autoGenerate = true) var sid: Int = 0,
    @ColumnInfo(name = "nom") var name: String,
    @ColumnInfo(name = "prenom") var prenom: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "tel") var tel: String,
    @ColumnInfo(name = "adresse") var adresse: String,
    @ColumnInfo(name = "DateNaissance") var DateNaissance: String?,
    @ColumnInfo(name = "date_creation") var date_creation: String?,
    @Ignore var attendance: Int?
    ) {
    constructor():this(0,"", "", "", "", "", "", "", null)
}