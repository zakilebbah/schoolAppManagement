package com.example.schoolapp.ui.Exam

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.Note
import com.example.schoolapp.viewModels.*
import java.util.HashMap
import android.widget.LinearLayout.LayoutParams
import androidx.core.view.marginTop
import java.math.BigDecimal
import java.math.RoundingMode

class AveragePage : AppCompatActivity() {
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MainApp).repositoryNote)
    }
    private val matiereViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((application as MainApp).repositoryMatier)
    }
    private val examsViewModel: ExamsViewModel by viewModels {
        ExamsViewModelFactory((application as MainApp).repositoryExamen)
    }

    private var average : TextView? = null
    private var name : TextView? = null
    private var linear2: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_average_page)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Moyenne")
        var sid: Int = intent.getIntExtra("sid", -1)
        var student_Name: String? = intent.getStringExtra("student_name")
        average = findViewById(R.id.average)
        name = findViewById(R.id.name)
        name!!.text = student_Name
        linear2 = findViewById(R.id.linear2)

        notesViewModel.searchNoteByStudent(sid).observe(this) { notes ->
            average(notes!!)
        }
    }
    fun average(notes: List<Note>) {
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

        average!!.text = BigDecimal(average00).setScale(2, RoundingMode.HALF_EVEN).toString()
        println(average.toString())
        println(mapMatiere.toString())
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}