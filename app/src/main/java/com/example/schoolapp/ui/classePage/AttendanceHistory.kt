package com.example.schoolapp.ui.classePage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Property
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.schoolapp.R
import com.example.schoolapp.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialCalendar
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


class AttendanceHistory : AppCompatActivity() {
    lateinit var calendar : CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_history)

        // val buton : Button = findViewById(R.id.button)
        // buton.setOnClickListener {
        showDataRangePicker()
        // }
    }


    private fun showDataRangePicker() {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()

        dateRangePicker.show(
            supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->

            //   val startDate = dateSelected.first
            //   val endDate = dateSelected.second

            //   if (startDate != null && endDate != null) {

            //   }
            //   val tvRange : TextView = findViewById(R.id.tvRangeDate)
            //      tvRange.text =
            //      "Reserved\nStartDate: ${convertLongToTime(startDate)}\n" +
            //                 "EndDate: ${convertLongToTime(endDate)}"
        }
    }

}


private fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat(
        "dd.MM.yyyy",
        Locale.getDefault())
    return format.format(date)
}


