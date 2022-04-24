package com.example.schoolapp.ui.Exam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ExamAdapter
import com.example.schoolapp.viewModels.ExamsViewModel
import com.example.schoolapp.viewModels.ExamsViewModelFactory
import com.example.schoolapp.viewModels.MatiereViewModelFactory
import com.example.schoolapp.viewModels.MatieresViewModel

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
        setTitle("Choose an exam")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        var cid: Int = intent.getIntExtra("cid", -1)
        var mid: Int = intent.getIntExtra("mid", -1)
        val adapter = ExamAdapter(ExamAdapter.OnClickListener { examen ->
            val intent = Intent(this, NotesList::class.java)
            intent.putExtra("cid", cid)
            intent.putExtra("mid", mid)
            intent.putExtra("eid", examen.Eid)
            startActivity(intent)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        examViewModel.allWords.observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            classes.let { adapter.submitList(it) }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}