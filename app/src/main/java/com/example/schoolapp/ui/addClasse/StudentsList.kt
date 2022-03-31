package com.example.schoolapp.ui.addClasse

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addStudent.AddStudentPage
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
        val adapter = StudentAdapter(StudentAdapter.OnClickListener { student ->
            val replyIntent = Intent()
            val array0 =  ArrayList<Int>()
            array0.add(0)
            array0.add(cid)
            array0.add(student.sid)
            replyIntent.putExtra(AddClassePage.EXTRA_REPLY, array0)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentsViewModel.allWords.observe(this) { students ->
            // Update the cached copy of the words in the adapter.
            students.let { adapter.submitList(it) }
        }
    }
    fun addStudentToClasse(student0: Student, cid: Int) {
        classeStudentsViewModel.insert(ClassRoom_Student(0, student0.sid, cid))
        finish()
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.Studentslistsql.REPLY"
    }
}