package com.example.schoolapp.ui.classePage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.CalendarView
import com.example.schoolapp.R
import java.util.*
import kotlin.collections.ArrayList

class AttendanceHistory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_history)
        var cal = findViewById<CalendarView>(R.id.calendarView)
        val calendar = Calendar.getInstance()
        calendar.set(2022, 4, 15)
        var calendars: List<Calendar> = listOf(calendar)
        cal.setHighlightedDays(calendars)
    }
}