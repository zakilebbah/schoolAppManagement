package com.example.schoolapp.data


import androidx.room.*

@Entity(tableName = "Note")
class Note (@PrimaryKey(autoGenerate = true) var Nid: Int = 0,
               @ColumnInfo(name = "id_student") var id_student: Int,
            @ColumnInfo(name = "id_classe") var id_classe: Int,
            @ColumnInfo(name = "id_examen") var id_examen: Int,
            @ColumnInfo(name = "note") var note: Double,
)
{

}
data class StudentWithNote(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "student_id",
        entityColumn = "sid"
    )
    val student: Student
)
