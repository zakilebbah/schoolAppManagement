package com.example.schoolapp.data

import androidx.room.*

@Entity(tableName = "ClassRoom_Student")
data class ClassRoom_Student(
    @PrimaryKey(autoGenerate = true) val csid: Int = 0,
    @ColumnInfo(name = "class_room_id") val class_room_id: Int,
    @ColumnInfo(name = "student_id") val student_id: Int,

    ) {

}
data class StudentWithclass(
    @Embedded val classRoom_Student: ClassRoom_Student,
    @Relation(
        parentColumn = "student_id",
        entityColumn = "sid"
    )
    val student: Student
)