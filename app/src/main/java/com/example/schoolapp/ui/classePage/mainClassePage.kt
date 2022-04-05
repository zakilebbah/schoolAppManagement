package com.example.schoolapp.ui.classePage

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.DateFormat.YEAR_ABBR_MONTH
import android.icu.text.DateFormat.getPatternInstance
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.adapters.StudentClasseAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.addClasse.StudentsList
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MainClassePage : AppCompatActivity() {
    private val classeActivityRequestCode = 2
    private val newClasseActivityRequestCode = 1
    private val deleteClasseActivityRequestCode = -1
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((application as MainApp).repositoryClasseStudent)
    }
    private val attendanceViewModel: AttendanceViewModel by viewModels {
        AttendanceModelFactory((application as MainApp).repositoryAttendance)
    }
    private val classeViewModel: ClasseViewModel by viewModels {
        WordViewModelFactory((application as MainApp).repositoryClasse)
    }
    private var dateView: TextView? = null
    private var recyclerView: RecyclerView? = null
    var cal = Calendar.getInstance()
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateView!!.text = sdf.format(cal.getTime())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_classe_page)

        setTitle("RRRRRR")
        var id: Int = intent.getIntExtra("id", -1)

        recyclerView = findViewById(R.id.recyclerview)
        dateView = findViewById(R.id.date)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date: String  =  LocalDateTime.now().format(formatter)
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("id", student.student.sid)
            startActivityForResult(intent, classeActivityRequestCode)}, attendanceViewModel, date)


        dateView!!.text = date

        Log.d("date date date", date)

        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged();

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                rebuildRecycle(id)


            }
        }
        dateView!!.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        classeStudentsViewModel.allStudents(id).observe(this) { classeStudents ->
            classeStudents.let { adapter.submitList(classeStudents) }
        }
        classeViewModel.classById(id).observe(this) { classe ->
            if (classe != null) setTitle(classe.name)

        }
        var fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {

            val intent = Intent(this, StudentsList::class.java)
            intent.putExtra("cid",id)
            startActivityForResult(intent, classeActivityRequestCode)
        }
    }
    fun rebuildRecycle(id0: Int) {
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("id", student.student.sid)
            startActivityForResult(intent, classeActivityRequestCode)}, attendanceViewModel, dateView!!.text.toString())
        recyclerView!!.setAdapter(null);
        recyclerView!!.setLayoutManager(null);
        recyclerView!!.setAdapter(adapter);
        recyclerView!!.setLayoutManager(LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        classeStudentsViewModel.allStudents(id0).observe(this) { classeStudents ->
            classeStudents.let { adapter.submitList(classeStudents) }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.classe_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()
        var id0: Int = intent.getIntExtra("id", -1)

        if (id == R.id.action_two) {
            val intent = Intent(this, AddClassePage::class.java)
            intent.putExtra("id",id0)
            startActivityForResult(intent, newClasseActivityRequestCode)
            return true
        }
        if (id == R.id.action_three) {
            val replyIntent = Intent()
            replyIntent.putExtra(AddClassePage.EXTRA_REPLY, id0)
            setResult(-10, replyIntent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == classeActivityRequestCode && resultCode == Activity.RESULT_OK) {
            Log.d("TEST TEST", "FZEFZE GFZEFZE ")
            intentData?.getIntegerArrayListExtra(StudentsList.EXTRA_REPLY)?.let { reply ->
                if (reply[0] != null) {
                    var classeWithStudent = ClassRoom_Student(reply[0], reply[1], reply[2], reply[3])
                    Log.d("TEST TEST reply", reply[1].toString())
                    classeStudentsViewModel.insert(classeWithStudent)
                }

            }
        } else if (requestCode == newClasseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayListExtra(AddClassePage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    var classe0 = Classe(0, reply[1], reply[2], reply[3])
                    classeViewModel.insert(classe0)
                    setTitle(classe0.name)
                }
                else {

                    var classe0 = Classe(reply[0].toInt(), reply[1], reply[2], reply[3])
                    classeViewModel.update(classe0)
                    setTitle(classe0.name)
                }

            }
        }

    }
}