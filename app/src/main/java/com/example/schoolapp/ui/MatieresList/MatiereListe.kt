package com.example.schoolapp.ui.MatieresList

import com.example.schoolapp.adapters.AddMatiereToClassAdapter
import com.example.schoolapp.data.ClasseMatiere
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.viewModels.*



import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.AddStudentToClasseAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.Exam.ExamsList
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.ui.classePage.StudentsList
import com.example.schoolapp.ui.studentsList.StudentsViewModel
import com.example.schoolapp.ui.studentsList.StudentsViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MatiereListe() : AppCompatActivity() {
    private val matiereViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    private val matiereClassViewModel: ClasseMatiereViewModel by viewModels {
        ClasseMatiereModelFactory((application as MainApp).repositoryClasseMatiere)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matiere_list)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var cid: Int = intent.getIntExtra("cid", -1)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val adapter = AddMatiereToClassAdapter(AddMatiereToClassAdapter.OnClickListener { matiere ->
            val intent = Intent(this, ExamsList::class.java)
            intent.putExtra("cid", cid)
            intent.putExtra("mid", matiere.Mid)
            startActivity(intent)

        }, cid)
        setTitle("Choose a subject")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        matiereViewModel.allMatiers.observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            classes.let { adapter.submitList(it) }
        }
        var fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, addMatierePage::class.java)
            intent.putExtra("id", -1)
            resultLauncher.launch(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val matiere0 = Matiere( 0, reply[1], reply[2].toDouble())
                    matiereViewModel.insert(matiere0)
                }
                else {
                    val matiere0 = Matiere( reply[0].toInt(), reply[1], reply[2].toDouble())
                    matiereViewModel.update(matiere0)
                }

            }
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.Studentslistsql.REPLY"
    }
}