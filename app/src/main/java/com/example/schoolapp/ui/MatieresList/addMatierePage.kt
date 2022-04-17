package com.example.schoolapp.ui.MatieresList

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.MatiereViewModelFactory
import com.example.schoolapp.viewModels.MatieresViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class addMatierePage : AppCompatActivity() {
    private val matieresViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    private lateinit var nom: EditText
    private lateinit var coef: EditText
    var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_matiere_page)
        setTitle("Matiere")
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        id = intent.getIntExtra("id", -1)
        nom = findViewById(R.id.nom)
        coef = findViewById(R.id.coef)
        val button = findViewById<Button>(R.id.button_save)
        initClassRoom()
        button.setOnClickListener {
            saveStudent()
        }
    }
    fun  saveStudent() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(nom.text) || TextUtils.isEmpty(coef.text) ) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val array0 =  ArrayList<String>()
            array0.add(id.toString())
            array0.add(nom.text.toString())
            array0.add(coef.text.toString())
            replyIntent.putExtra(AddStudentPage.EXTRA_REPLY, array0)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }
    private fun initClassRoom() {
        Log.d("TEST ", id.toString())
        if (id != -1) {
            var  matiere: Matiere = matieresViewModel.loadById(id)
            nom.setText(matiere.name.toString())
            coef.setText(matiere.coef.toString())
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}