package com.example.schoolapp.ui.MatieresList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ExamAdapter
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.data.Examen
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.Exam.AddExam
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.ui.studentsList.StudentsViewModel
import com.example.schoolapp.ui.studentsList.StudentsViewModelFactory
import com.example.schoolapp.viewModels.ExamsViewModel
import com.example.schoolapp.viewModels.ExamsViewModelFactory
import com.example.schoolapp.viewModels.MatiereViewModelFactory
import com.example.schoolapp.viewModels.MatieresViewModel

class MatierePage : AppCompatActivity() {
    private val examViewModel: ExamsViewModel by viewModels {
        ExamsViewModelFactory((application as MainApp).repositoryExamen)
    }
    private val matieresViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    var id: Int = -1
    private var addButton : Button? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: ExamAdapter? = null
    private var nameMatiere: TextView? = null
    private var coef: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matiere_page)
        setTitle("Matiere")
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        id = intent.getIntExtra("id", -1)
        recyclerView = findViewById(R.id.recyclerview)
        nameMatiere = findViewById(R.id.nameMatiere)
        coef = findViewById(R.id.coef)
        initMatiere(id)
        adapter = ExamAdapter(ExamAdapter.OnClickListener { exam ->
//            val intent = Intent(this, AddStudentPage::class.java)
//            intent.putExtra("id", student.sid)
//            resultLauncher.launch(intent)
        })
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        examViewModel.loadByMatiere(id).observe(this) { exams ->
            // Update the cached copy of the words in the adapter.
            exams.let { adapter!!.submitList(it) }
        }
        addButton= findViewById(R.id.button)
        addButton!!.setOnClickListener {
            val intent = Intent(this, AddExam::class.java)
            intent.putExtra("eid",-1)
            resultLauncher.launch(intent)
        }

    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val student0 = Examen( 0, reply[1], 1, id, reply[2])
                    examViewModel.insert(student0)
                }
                else {
                    val student0 = Examen( reply[0].toInt(), reply[1], 1, id, reply[2])
                    examViewModel.update(student0)
                }

            }
        }
    }
    fun initMatiere(id0: Int) {
        var mat =  matieresViewModel.loadById(id0)
        nameMatiere!!.text = mat.name
        coef!!.text = mat.coef.toString()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}