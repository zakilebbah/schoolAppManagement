package com.example.schoolapp.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note")
class Note (@PrimaryKey(autoGenerate = true) var Nid: Int = 0,
               @ColumnInfo(name = "id_student") var id_student: Int,
            @ColumnInfo(name = "id_classe") var id_classe: Int,
            @ColumnInfo(name = "id_examen") var id_examen: Int,)
{

}
