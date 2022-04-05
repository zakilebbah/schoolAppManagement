package com.example.schoolapp.ui.addClasse

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
import com.example.schoolapp.data.AppDatabase
import com.example.schoolapp.data.Classe
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class AddClassePage : AppCompatActivity() {
    private val classeViewModel: ClasseViewModel by viewModels {
        WordViewModelFactory((application as MainApp).repositoryClasse)
    }
    var id: Int = -1
    private lateinit var name: EditText
    private lateinit var grade: EditText
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_classe_page)
        name = findViewById(R.id.name)
        grade = findViewById(R.id.grade)
        id = intent.getIntExtra("id", -1)
        Log.d("DZDZDZD", id.toString())
        val button = findViewById<Button>(R.id.button_save)
        if (id == -1) {
            button.text = "Save"
        }
        else {
            button.text = "Update"
        }
        initClassRoom()
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(name.text) || TextUtils.isEmpty(grade.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name0 = name.text.toString()
                val grade0 = grade.text.toString()
                val date0 = Date().getTime().toString()
                val array0 =  ArrayList<String>()
                array0.add(id.toString())
                array0.add(name0)
                array0.add(grade0)
                array0.add(date0)
                replyIntent.putExtra(EXTRA_REPLY, array0)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.studentslistsql.REPLY"
    }
    private fun initClassRoom() {

        if (id != -1) {
            classeViewModel.classById(id).observe(this) { classe ->
                name.setText(classe.name.toString())
                grade.setText(classe.grade.toString())
                Log.d("date date ", classe.date.toString())
            }


        }
    }
}