package com.example.schoolapp.ui.classePage

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.StudentClasseAdapter
import com.example.schoolapp.data.*
import com.example.schoolapp.ui.Exam.AveragePage
import com.example.schoolapp.ui.MatieresList.MatiereListe
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.ui.studentsList.StudentsViewModel
import com.example.schoolapp.ui.studentsList.StudentsViewModelFactory
import com.example.schoolapp.viewModels.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal
import java.math.RoundingMode
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
        ClasseViewModelFactory((application as MainApp).repositoryClasse)
    }
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MainApp).repositoryNote)
    }
    private val matiereViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    private val examsViewModel: ExamsViewModel by viewModels {
        ExamsViewModelFactory((application as MainApp).repositoryExamen)
    }
    private val studentsViewModel: StudentsViewModel by viewModels {
        StudentsViewModelFactory((application as MainApp).repositoryStudent)
    }
    private var dateView: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var nameClasse : TextView? = null
    private var gradeClass : TextView? = null
    private var nbrEtudiant : TextView? = null
    private var date: String = ""
    var id = -1
    var cal = Calendar.getInstance()
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateView!!.text = sdf.format(cal.getTime())
        date = sdf.format(cal.getTime())
    }
    var student_name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_classe_page)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Classe")
        id = intent.getIntExtra("id", -1)
        nameClasse= findViewById(R.id.nameClass)
        gradeClass = findViewById(R.id.gradeClass)
        recyclerView = findViewById(R.id.recyclerview)
        dateView = findViewById(R.id.date)
        date =  LocalDateTime.now().format(formatter)
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            var intent = Intent(this, AveragePage::class.java)
            intent.putExtra("sid", student.student.sid)
            intent.putExtra("student_name", student.student.name + " " + student.student.prenom)
            startActivity(intent)
        },
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
        val button: Button = findViewById(R.id.afficher_notes_button)
        button.setOnClickListener {
            val intent = Intent(this, MatiereListe::class.java)
            intent.putExtra("cid",id)
            startActivityForResult(intent, classeActivityRequestCode)
        }
        notesViewModel.allNotes.observe(this) {notes ->
            classeStudentsViewModel.allStudents(id).observe(this) { classeStudents ->
                nbrEtudiant = findViewById(R.id.nbretudiant)
                nbrEtudiant?.text = classeStudents.size.toString()
                for (i in classeStudents.indices) {
                    var nots00 = notes.filter { note -> note.id_student ==  classeStudents[i].student.sid}
//                    var notes00 = notesViewModel.searchNoteByStudentList(classeStudents[i].student.sid)
                    if (nots00.size > 0) {
                        classeStudents[i].student.note =  BigDecimal(average(nots00!!)).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    }
                }

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

        classeViewModel.classById(id).observe(this) { classe ->
            if (classe != null) {
                nameClasse?.text = classe.name
                gradeClass?.text=classe.grade
            }

        }
        var fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {

            val intent = Intent(this, AddStudentPage::class.java)
            intent.putExtra("id",-1)
            startActivityForResult(intent, classeActivityRequestCode)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    fun rebuildRecycle(id0: Int, date: String) {
        val adapter = StudentClasseAdapter(StudentClasseAdapter.OnClickListener { student ->
            var intent = Intent(this, AveragePage::class.java)
            intent.putExtra("sid", student.student.sid)
            intent.putExtra("student_name", student.student.name + " " + student.student.prenom)
            startActivity(intent)
        },
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

            intentData?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    var student0 = Student( 0, reply[1], reply[2], reply[3], reply[4], reply[5], reply[6], reply[7], null, null)
                    var id00 = studentsViewModel.insertClassePage(student0)
                    var classeWithStudent = ClassRoom_Student(0, id, id00.toInt(), 0)
                    classeStudentsViewModel.insert(classeWithStudent)
                }

            }
        }

    }
    fun average(notes: List<Note>): Double {
        println(notes.size.toString())
        val map = HashMap<String, MutableList<Double>>()
        val mapAverage = HashMap<String, Double>()
        val mapMatiere=  HashMap<String, HashMap<String, String>>()
        var average00 = 0.0

        for (i in notes.indices) {
            val list00 : MutableList<Double> = mutableListOf()
            var exam = examsViewModel.examById(notes[i].id_examen)
            var mat =  matiereViewModel.loadById(exam.id_matiere)
            var map11 =HashMap<String, String>()
            map11["coef"] = mat.coef.toString()
            map11["name"] = mat.name
            map11["moy"]  = "0.0"
            mapMatiere[mat.Mid.toString()] =  map11
            if (map[mat.Mid.toString()] != null) {
                map[mat.Mid.toString()]?.add(notes[i].note)
            }
            else {
                list00.add(notes[i].note)
                map[mat.Mid.toString()] = list00
            }
        }
        var coefSum = 0.0
        for ((key, value) in map) {
            var sum = 0.0
            for (i in value) {
                sum += i
            }
            var examAverige= sum/value.size.toDouble()
            mapMatiere[key]!!["moy"] = examAverige.toString()
            mapAverage[key] = examAverige
            average00 += examAverige*mapMatiere[key]!!["coef"]!!.toDouble()
            coefSum +=mapMatiere[key]!!["coef"]!!.toDouble()
        }
        average00 /= coefSum
        return  average00

//        average!!.text = BigDecimal(average00).setScale(2, RoundingMode.HALF_EVEN).toString()
//        println(average.toString())
//        println(mapMatiere.toString())
//        buildCard(mapMatiere)
    }
}


