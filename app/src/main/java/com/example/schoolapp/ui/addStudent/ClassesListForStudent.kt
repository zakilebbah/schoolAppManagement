package com.example.schoolapp.ui.addStudent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ClasseAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.classePage.MainClassePage
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.ClasseViewModelFactory

class ClassesListForStudent : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val classeViewModel: ClasseViewModel by viewModels {
        ClasseViewModelFactory((application as MainApp).repositoryClasse)
    }
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((application as MainApp).repositoryClasseStudent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classes_list_for_student)
        setTitle("Choisissez une classe")
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.recyclerview)
        val adapter = ClasseAdapter(ClasseAdapter.OnClickListener { classe ->
            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("cid", classe.cid)
            resultLauncher.launch(intent)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        classeViewModel.allWords.observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            for (i in classes.indices) {
                classes[i].nbr = classeStudentsViewModel.getStudentNumber(classes[i].cid)
            }
            classes.let { adapter.submitList(it) }
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                val replyIntent = Intent()
                replyIntent.putExtra(AddStudentPage.EXTRA_REPLY, reply)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}