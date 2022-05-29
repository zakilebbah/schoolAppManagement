package com.example.schoolapp

import com.example.schoolapp.data.Matiere
import com.example.schoolapp.data.Note
import com.example.schoolapp.ui.Exam.AveragePage
import org.junit.Test

import org.junit.Assert.*
import java.util.HashMap

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun moy_isCorrect() {
        val utils: Utils = Utils()
        var notes: MutableList<Note> = mutableListOf()
        var matiers: MutableList<Matiere> = mutableListOf()
        val values = HashMap<String, HashMap<String, String>>()
        notes.add(Note(Nid = 0, id_student = 0, id_classe = 0, id_examen = 0, note = 10.0))
        notes.add(Note(Nid = 1, id_student = 1, id_classe = 1, id_examen = 1, note = 12.5))
        notes.add(Note(Nid = 0, id_student = 2, id_classe = 2, id_examen = 2, note = 9.0))
        notes.add(Note(Nid = 3, id_student = 3, id_classe = 3, id_examen = 3, note = 15.75))
        notes.add(Note(Nid = 4, id_student = 4, id_classe = 4, id_examen = 4, note = 11.25))
        matiers.add(Matiere(Mid = 0, name = "DSS", coef = 3.0))
        matiers.add(Matiere(Mid = 1, name = "APM", coef = 2.0))
        for (i in notes.indices) {
            var mat: Matiere? = null
            if (i < 3) {
                mat = matiers[0]
            }
            else {
                mat = matiers[1]
            }
            var val00 = HashMap<String, String>()
            val00["coef"] = mat.coef.toString()
            val00["name"] = mat.name
            val00["Mid"] = mat.Mid.toString()
            val00["moy"]  = "0.0"
            values[notes[i].id_examen.toString()] = val00
        }
        utils.calc_average(notes, values)
        print("av: " + utils.average)
        assertEquals(11.70, utils.average.toDouble(), 1.0)
    }
}