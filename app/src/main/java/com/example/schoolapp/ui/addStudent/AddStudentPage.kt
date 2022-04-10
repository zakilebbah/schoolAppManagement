package com.example.schoolapp.ui.addStudent

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.Student
import com.example.schoolapp.databinding.ActivityAddClassePageBinding
import com.example.schoolapp.databinding.ActivityAddStudentPageBinding
import com.example.schoolapp.databinding.ActivityClassePageBinding
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.dashboard.StudentsViewModel
import com.example.schoolapp.ui.dashboard.StudentsViewModelFactory
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

class AddStudentPage : AppCompatActivity() {
    private lateinit var binding: ActivityAddStudentPageBinding
    private val studentsViewModel: StudentsViewModel by viewModels {
        StudentsViewModelFactory((application as MainApp).repositoryStudent)
    }
    private lateinit var nom:EditText
    private lateinit var prenom:EditText
    private lateinit var email:EditText
    private lateinit var tel:EditText
    private lateinit var adresse:EditText
    private lateinit var dateNaissance:EditText
    var id: Int = -1


    private lateinit var linear_layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Student details")
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        id = intent.getIntExtra("id", -1)
        linear_layout = findViewById(R.id.formsList)
        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        email = findViewById(R.id.email)
        tel = findViewById(R.id.tel)
        adresse = findViewById(R.id.adresse)
        dateNaissance = findViewById(R.id.dateNaissance)
        val button = findViewById<Button>(R.id.button_save)
        initClassRoom()
        button.setOnClickListener {
            saveStudent()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    @SuppressLint("SimpleDateFormat")
    fun  saveStudent() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(nom.text) || TextUtils.isEmpty(prenom.text) || TextUtils.isEmpty(email.text) ) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val array0 =  ArrayList<String>()
            array0.add(id.toString())
            array0.add(nom.text.toString())
            array0.add(prenom.text.toString())
            array0.add(email.text.toString())
            array0.add(tel.text.toString())
            array0.add(adresse.text.toString())
            array0.add(dateNaissance.text.toString())
            array0.add(currentDate.toString())
            replyIntent.putExtra(EXTRA_REPLY, array0)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.studentlistsql.REPLY"
    }

    private fun initClassRoom() {
        Log.d("TEST ", id.toString())
        if (id != -1) {
            var  student: Student = studentsViewModel.studentById(id)
            nom.setText(student.name.toString())
            prenom.setText(student.prenom.toString())
            email.setText(student.email.toString())
            tel.setText(student.tel.toString())
            adresse.setText(student.adresse.toString())
            dateNaissance.setText(student.DateNaissance.toString())


        }
    }
}