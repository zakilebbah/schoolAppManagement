package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
@Entity(tableName = "Examen")
class Examen (@PrimaryKey(autoGenerate = true) var Eid: Int = 0,
              @ColumnInfo(name = "nom") var name: String,
              @ColumnInfo(name = "type") var type: Int,
              @ColumnInfo(name = "id_matiere") var id_matiere: Int,
//              @ColumnInfo(name = "id_classe") var id_classe: Int,
              @ColumnInfo(name = "date") var date: String)
{

}




