package com.example.schoolapp.ui.Exam

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import com.example.schoolapp.MainApp
import com.example.schoolapp.data.Note
import com.example.schoolapp.viewModels.*
import java.util.HashMap
import android.widget.LinearLayout.LayoutParams
import androidx.core.view.marginTop
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.math.BigDecimal
import java.math.RoundingMode
import com.github.mikephil.charting.data.RadarData

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet

import com.github.mikephil.charting.data.RadarDataSet

import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.components.Legend

import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.formatter.IAxisValueFormatter

import com.github.mikephil.charting.animation.Easing

import android.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.example.schoolapp.Utils
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.studentsList.StudentsViewModel
import com.example.schoolapp.ui.studentsList.StudentsViewModelFactory

import com.github.mikephil.charting.components.MarkerView






data class Score(
    val name:String,
    val score: Int,
)

class AveragePage : AppCompatActivity() {
    private val utils: Utils = Utils()
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
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((application as MainApp).repositoryClasseStudent)
    }
    private var scoreList = ArrayList<Score>()
    var mapMatiere=  HashMap<String, HashMap<String, String>>()
    private lateinit var chart: RadarChart
    private var average : TextView? = null
    private var name : TextView? = null
    private var linear2: LinearLayout? = null
    private var sid = 0
    private var cid = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.schoolapp.R.layout.activity_average_page)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Détail étudiant")
        sid= intent.getIntExtra("sid", -1)
        cid= intent.getIntExtra("cid", -1)
        var student_Name: String? = intent.getStringExtra("student_name")
        average = findViewById(com.example.schoolapp.R.id.average)
        name = findViewById(com.example.schoolapp.R.id.name)
        name!!.text = student_Name
        linear2 = findViewById(com.example.schoolapp.R.id.linear2)
        chart = findViewById(com.example.schoolapp.R.id.barChart)
        notesViewModel.searchNoteByStudent(sid).observe(this) { notes ->
            if (notes.size > 0) {
                calculateAvergages(notes!!)
                initBarChart()
            }

        }


    }
    fun calculateAvergages(notes: List<Note>) {
        val values = HashMap<String, HashMap<String, String>>()
        for (i in notes.indices) {
            var exam = examsViewModel.examById(notes[i].id_examen)
            var mat =  matiereViewModel.loadById(exam.id_matiere)
            if (!values.containsKey(exam.Eid.toString())) {
                var val00 = HashMap<String, String>()
                val00["coef"] = mat.coef.toString()
                val00["name"] = mat.name
                val00["Mid"] = mat.Mid.toString()
                val00["moy"]  = "0.0"
                values[exam.Eid.toString()] = val00
            }

        }
        utils.calc_average(notes, values)
        mapMatiere = utils.mapMatiere
        average!!.text = utils.average
        buildCard(mapMatiere)
    }


    fun buildCard(map: HashMap<String, HashMap<String, String>>) {
        for ((key, value) in map) {
            val card_view = CardView(this)
            // Initialize a new LayoutParams instance, CardView width and height
            val layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, // CardView width
                LayoutParams.WRAP_CONTENT // CardView height
            )
            layoutParams.setMargins(20, 20,20, 20)
            card_view.layoutParams = layoutParams
            card_view.radius = 12F
            card_view.setContentPadding(25,25,25,25)
            card_view.setCardBackgroundColor(Color.WHITE)
            card_view.cardElevation = 8F
            card_view.maxCardElevation = 12F
            card_view.addView(buildCardView(value))
            linear2!!.addView(card_view)
        }
    }
    fun buildCardView(map: HashMap<String, String>): LinearLayout {
        val linear = LinearLayout(this)
        linear.layoutParams =LayoutParams(
            LayoutParams.MATCH_PARENT, // CardView width
            LayoutParams.WRAP_CONTENT // CardView height
        )
        linear.setOrientation(LinearLayout.VERTICAL)
        var text = TextView(this)
        text.text = map["name"]
        text.setTypeface(text.getTypeface(), Typeface.BOLD);
        text.setTextSize(20F)
        linear.addView(text)
        text = TextView(this)
        text.text = "Coef: " + map["coef"]
        text.setTypeface(text.getTypeface(), Typeface.ITALIC);
        linear.addView(text)
        text = TextView(this)
        text.text = "Moy: " + map["moy"]
        text.setTypeface(text.getTypeface(), Typeface.ITALIC);
        linear.addView(text)
        return  linear
    }
    private fun initBarChart() {
        var labels = arrayListOf<String>()
        val entries: ArrayList<RadarEntry> = ArrayList()
        for ((key, value) in mapMatiere) {
            entries.add(RadarEntry(value["moy"]!!.toFloat()))
            labels.add(value["name"]!!)
        }
        var radarDataSet: RadarDataSet = RadarDataSet(entries,"Entry 1")
        radarDataSet.setColor(Color.RED)
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(12f);

        var radarData  = RadarData();
        radarData.addDataSet(radarDataSet);
        var xAxis = chart.getXAxis();
        xAxis.setValueFormatter(IndexAxisValueFormatter(labels));
        chart.description.isEnabled = false
        chart.setData(radarData);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()
        var id0: Int = intent.getIntExtra("id", -1)

        if (id == com.example.schoolapp.R.id.action_three) {
            classeStudentsViewModel.deleteByCidSid(cid, sid)
            studentsViewModel.delete(sid)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.example.schoolapp.R.menu.student, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

