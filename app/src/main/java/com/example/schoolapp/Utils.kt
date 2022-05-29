package com.example.schoolapp

import com.example.schoolapp.data.Note
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.HashMap

class Utils() {
    var mapMatiere=  HashMap<String, HashMap<String, String>>()
    var average = ""
    fun calc_average(notes: List<Note>, values: HashMap<String, HashMap<String, String>>) {
        mapMatiere = HashMap<String, HashMap<String, String>>()
        average = ""
        println(notes.size.toString())
        val map = HashMap<String, MutableList<Double>>()
        val mapAverage = HashMap<String, Double>()
        var average00 = 0.0

        for (i in notes.indices) {
            val list00 : MutableList<Double> = mutableListOf()
            var mid: String = values[notes[i].id_examen.toString()]?.get("Mid")!!
//            var exam = examsViewModel.examById(notes[i].id_examen)
//            var mat =  matiereViewModel.loadById(exam.id_matiere)
//            var map11 =HashMap<String, String>()
//            map11["coef"] = mat.coef.toString()
//            map11["name"] = mat.name
//            map11["moy"]  = "0.0"
            mapMatiere[mid] =  values[notes[i].id_examen.toString()]!!
            if (map[mid] != null) {
                map[mid]?.add(notes[i].note)
            }
            else {
                list00.add(notes[i].note)
                map[mid] = list00
            }
        }
        var coefSum = 0.0
        println(map)

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
            println(mapMatiere)

        }
        average00 /= coefSum
        average = BigDecimal(average00).setScale(2, RoundingMode.HALF_EVEN).toString()
    }
}