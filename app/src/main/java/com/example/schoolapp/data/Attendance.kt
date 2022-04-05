package com.example.schoolapp.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
class Attendance(
    @PrimaryKey(autoGenerate = true) val aid: Int = 0,
    @ColumnInfo(name = "student_id") val student_id: Int,
    @ColumnInfo(name = "classe_id") val classe_id: Int,
    @ColumnInfo(name = "status") val status: Int,
    @ColumnInfo(name = "date") val date: String,

    ) {

}