package com.example.schoolapp.data

import androidx.room.*

@Entity(tableName = "ClasseMatiere")
class ClasseMatiere (@PrimaryKey(autoGenerate = true) var CMid: Int = 0,
               @ColumnInfo(name = "id_classe") var id_classe: Int,
               @ColumnInfo(name = "id_matiere") var id_matiere: Int,)
{

}
data class ClasseWithMatiere(
    @Embedded val classeMatiere: ClasseMatiere,
    @Relation(
        parentColumn = "id_matiere",
        entityColumn = "Mid"
    )
    val matiere: Matiere
)