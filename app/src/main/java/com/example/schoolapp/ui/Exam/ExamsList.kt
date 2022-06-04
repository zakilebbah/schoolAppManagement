package com.example.schoolapp.ui.Exam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ExamAdapter
import com.example.schoolapp.data.Examen
import com.example.schoolapp.ui.MatieresList.addMatierePage
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.ExamsViewModel
import com.example.schoolapp.viewModels.ExamsViewModelFactory
import com.example.schoolapp.viewModels.MatiereViewModelFactory
import com.example.schoolapp.viewModels.MatieresViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExamsList : AppCompatActivity() {
    private val examViewModel: ExamsViewModel by viewModels {
        ExamsViewModelFactory((application as MainApp).repositoryExamen)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exams_list)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        val MatierName: TextView = findViewById(R.id.name)
        var cid: Int = intent.getIntExtra("cid", -1)
        var mid: Int = intent.getIntExtra("mid", -1)
        var name: String? = intent.getStringExtra("name")
        MatierName.text = "Classe: " + name!!
        setTitle("Choisissez un examen")
        val adapter = ExamAdapter(ExamAdapter.OnClickListener { examen ->
            val intent = Intent(this, NotesList::class.java)
            intent.putExtra("cid", cid)
            intent.putExtra("mid", mid)
            intent.putExtra("eid", examen.Eid)
            startActivity(intent)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        examViewModel.loadByMatiere(mid).observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            classes.let { adapter.submitList(it) }
        }
        var fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddExam::class.java)
            intent.putExtra("eid",-1)
            resultLauncher.launch(intent)
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        var mid: Int = intent.getIntExtra("mid", -1)
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val student0 = Examen( 0, reply[1], 1, mid, reply[2])
                    examViewModel.insert(student0)
                }
                else {
                    val student0 = Examen( reply[0].toInt(), reply[1], 1, mid, reply[2])
                    examViewModel.update(student0)
                }

            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}