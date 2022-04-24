package com.example.schoolapp.ui.Exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.NoteAdapter
import com.example.schoolapp.data.Note
import com.example.schoolapp.viewModels.*
import com.example.schoolapp.ui.Exam.IList
interface IList {
    fun addToList(editTextValue: String?)
}
class NotesList : AppCompatActivity() {
    private val matiereViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    private val classeViewModel: ClasseViewModel by viewModels {
        WordViewModelFactory((application as MainApp).repositoryClasse)
    }
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((application as MainApp).repositoryClasseStudent)
    }
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MainApp).repositoryNote)
    }
    private var recyclerView: RecyclerView? = null
    private var nameClasse : TextView? = null
    private var matiere : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Notes")
        nameClasse= findViewById(R.id.nameClass)
        matiere = findViewById(R.id.matiere)
        var textChange = object : IList {
            override fun addToList(editTextValue: String?) {
                Log.d("TEST", editTextValue!!)
            }
        }
        recyclerView = findViewById(R.id.recyclerview)
        var cid: Int = intent.getIntExtra("cid", -1)
        var mid: Int = intent.getIntExtra("mid", -1)
        var eid: Int = intent.getIntExtra("eid", -1)
        val adapter = NoteAdapter(textChange)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        matiere!!.text =  matiereViewModel.loadById(mid).name
        classeViewModel.classById(cid).observe(this) { classe ->
            // Update the cached copy of the words in the adapter.
            nameClasse!!.text = classe.name
        }
        classeStudentsViewModel.allStudents(cid).observe(this) { classeStudents ->
            for (i in classeStudents.indices) {
                var note: Note = notesViewModel.searchNote(classeStudents[i].student.sid, eid, cid)
                if (note != null) {
                    classeStudents[i]!!.student.note = note.note
                }
            }
            classeStudents.let { adapter.submitList(classeStudents) }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun addToList(editTextValue: String?) {
        //TODO("logic for adding to list and sending")
    }
}
