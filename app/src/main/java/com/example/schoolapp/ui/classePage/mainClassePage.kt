package com.example.schoolapp.ui.classePage

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.StudentClasseAdapter
import com.example.schoolapp.data.*
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
    private var nameClasse : TextView? = null
    private var gradeClass : TextView? = null
    private var nbrEtudiant : TextView? = null
    private var date: String = ""
    var cal = Calendar.getInstance()
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateView!!.text = sdf.format(cal.getTime())
        date = sdf.format(cal.getTime())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_classe_page)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Classe")
        var id: Int = intent.getIntExtra("id", -1)
        nameClasse= findViewById(R.id.nameClass)
        gradeClass = findViewById(R.id.gradeClass)
        recyclerView = findViewById(R.id.recyclerview)
        dateView = findViewById(R.id.date)
        date =  LocalDateTime.now().format(formatter)
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("id", student.student.sid)
            startActivityForResult(intent, classeActivityRequestCode)},
            StudentClasseAdapter.OnClickListener2 { student, status ->
                studentAttendanceClick(student, status)}, date)
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
                rebuildRecycle(id, date)
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
            nbrEtudiant = findViewById(R.id.nbretudiant)
            nbrEtudiant?.text = classeStudents.size.toString()
            for (i in classeStudents.indices) {
                var attendance: Attendance = attendanceViewModel.searchByDate(date, classeStudents[i]!!.student.sid,
                    classeStudents[i]!!.classRoom_Student.class_room_id)
                if (attendance != null) {
                    classeStudents[i]!!.student.attendance = attendance.status
                }
            }
            classeStudents.let { adapter.submitList(classeStudents) }
        }

        classeViewModel.classById(id).observe(this) { classe ->
            if (classe != null) {
                nameClasse?.text = classe.name
                gradeClass?.text=classe.grade
            }

        }
        var fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {

            val intent = Intent(this, StudentsList::class.java)
            intent.putExtra("cid",id)
            startActivityForResult(intent, classeActivityRequestCode)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun rebuildRecycle(id0: Int, date: String) {
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("id", student.student.sid)
            startActivityForResult(intent, classeActivityRequestCode)},
            StudentClasseAdapter.OnClickListener2 { student, status ->
                studentAttendanceClick(student, status)
                }, dateView!!.text.toString())
        recyclerView!!.setAdapter(adapter);
        recyclerView!!.setLayoutManager(LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        classeStudentsViewModel.allStudents(id0).observe(this) { classeStudents ->
            for (i in classeStudents.indices) {
                var attendance: Attendance = attendanceViewModel.searchByDate(date, classeStudents[i]!!.student.sid,
                    classeStudents[i]!!.classRoom_Student.class_room_id)
                if (attendance != null) {
                    classeStudents[i]!!.student.attendance = attendance.status
                }
            }
            classeStudents.let { adapter.submitList(classeStudents) }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.classe_menu, menu)
        return true
    }
    fun studentAttendanceClick(student: StudentWithclass, status: Int) {
        var attendance: Attendance = attendanceViewModel.searchByDate(date, student!!.student.sid,
            student!!.classRoom_Student.class_room_id)
        if (attendance != null) {
            attendanceViewModel.update(Attendance(attendance.aid, student.classRoom_Student.student_id,
                student.classRoom_Student.class_room_id, status, date))
        }
        else {
            attendanceViewModel.insert(Attendance(0, student.classRoom_Student.student_id,
                student.classRoom_Student.class_room_id, status, date))
        }
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

                }
                else {

                    var classe0 = Classe(reply[0].toInt(), reply[1], reply[2], reply[3])
                    classeViewModel.update(classe0)

                }

            }
        }

    }
}