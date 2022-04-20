package com.example.schoolapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Classe::class, Student::class,
    ClassRoom_Student::class, Attendance::class, Matiere::class,
    Examen::class, Note::class, ClasseMatiere::class], version = 10, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun classeDao(): ClasseDao
    abstract fun studentDao(): StudentDao
    abstract fun ClassRoomStudentDao(): ClassRoom_Student_Dao
    abstract fun AttendanceDao(): AttendanceDao
    abstract fun MatiereDAO(): MatiereDAO
    abstract fun ExamenDAO(): ExamenDAO
    abstract fun NoteDAO(): NoteDAO
    abstract fun ClasseMatiereDao(): ClasseMatiereDao
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var classeDao0 = database.classeDao()

                    // Delete all content here.
                    classeDao0.deleteAll()

                    // Add sample words.
                    var classe = Classe(cid = 0, name = "classe 0", grade = "1", date = "26-03-2022")
                    classeDao0.insertClass(classe)

                }
            }
        }
    }
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "school"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(WordDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}