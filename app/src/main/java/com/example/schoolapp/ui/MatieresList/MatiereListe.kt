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
import com.example.schoolapp.ui.addStudent.AddStudentPage
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
        setContentView(R.layout.activity_students_list)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var cid: Int = intent.getIntExtra("cid", -1)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val adapter = AddMatiereToClassAdapter(AddMatiereToClassAdapter.OnClickListener { matiere, type ->
            if (type == 0) {
                if (matiereClassViewModel.loadCidMid(cid,matiere.Mid) == null) {
                    matiereClassViewModel.insert(ClasseMatiere(0, cid, matiere.Mid))
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
                matiereClassViewModel.deleteByCidMid(cid, matiere.Mid)
            }

        }, cid)
        setTitle("Students")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun addStudentToClasse(matiere0: Matiere, cid: Int) {
        matiereClassViewModel.insert(ClasseMatiere(0, matiere0.Mid, cid))
        finish()
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.Studentslistsql.REPLY"
    }
}