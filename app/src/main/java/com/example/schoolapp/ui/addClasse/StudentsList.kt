package com.example.schoolapp.ui.addClasse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.AddStudentToClasseAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.dashboard.StudentsViewModel
import com.example.schoolapp.ui.dashboard.StudentsViewModelFactory
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel

class StudentsList() : AppCompatActivity() {
    private val studentsViewModel: StudentsViewModel by viewModels {
        StudentsViewModelFactory((application as MainApp).repositoryStudent)
    }
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((application as MainApp).repositoryClasseStudent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)
        var cid: Int = intent.getIntExtra("cid", -1)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val adapter = AddStudentToClasseAdapter(AddStudentToClasseAdapter.OnClickListener { student, type ->
            if (type == 0) {
                if (classeStudentsViewModel.loadCidSid(cid, student.sid) == null) {
                    classeStudentsViewModel.insert(ClassRoom_Student(0, cid, student.sid, 0))
                }
                else {
                    Toast.makeText(
                this,
                "Student exists in class room",
                Toast.LENGTH_LONG
            ).show()
                }
            }

            else {
                classeStudentsViewModel.deleteByCidSid(cid, student.sid)
            }

        }, classeStudentsViewModel, cid)
        setTitle("Students")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentsViewModel.allWords.observe(this) { students ->
            // Update the cached copy of the words in the adapter.
            students.let { adapter.submitList(it) }
        }
    }
    fun addStudentToClasse(student0: Student, cid: Int) {
        classeStudentsViewModel.insert(ClassRoom_Student(0, student0.sid, cid, 0))
        finish()
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.Studentslistsql.REPLY"
    }
}