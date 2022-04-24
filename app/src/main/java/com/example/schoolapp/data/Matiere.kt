package com.example.schoolapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Matiere")
class Matiere (@PrimaryKey(autoGenerate = true) val Mid: Int = 0,
              @ColumnInfo(name = "nom") var name: String,
              @ColumnInfo(name = "coef") var coef: Double,)
{

}
