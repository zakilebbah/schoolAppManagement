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
import com.example.schoolapp.R
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addClasse.AddClassePage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.full.memberProperties

class AddStudentPage : AppCompatActivity() {
    private lateinit var nom:EditText
    private lateinit var prenom:EditText
    private lateinit var email:EditText
    private lateinit var tel:EditText
    private lateinit var adresse:EditText
    private lateinit var dateNaissance:EditText



    private lateinit var linear_layout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student_page)
        linear_layout = findViewById(R.id.formsList)
        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        email = findViewById(R.id.email)
        tel = findViewById(R.id.tel)
        adresse = findViewById(R.id.adresse)
        dateNaissance = findViewById(R.id.dateNaissance)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            saveStudent()
        }
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

//    fun initForms() {
//        for (key in Student::class.memberProperties) {
//            Log.d("Student Student Student", key.name)
//        }
//    }
}