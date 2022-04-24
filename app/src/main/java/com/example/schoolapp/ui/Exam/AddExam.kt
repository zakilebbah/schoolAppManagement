package com.example.schoolapp.ui.Exam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.schoolapp.R
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.ui.classePage.StudentsList

class AddExam : AppCompatActivity() {
    var id: Int = -1
    private lateinit var nom: EditText
    private lateinit var date: EditText
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exam)
        id = intent.getIntExtra("eid", -1)
        nom = findViewById(R.id.nom)
        date = findViewById(R.id.date)
        button = findViewById(R.id.button_save)
        button.setOnClickListener {
            addExam()
        }
    }
    fun addExam() {
        val replyIntent = Intent()
        val array0 =  ArrayList<String>()
        array0.add(id.toString())
        array0.add(nom.text.toString())
        array0.add(date.text.toString())
        replyIntent.putExtra(AddStudentPage.EXTRA_REPLY, array0)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }
}