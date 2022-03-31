package com.example.schoolapp.ui.addClasse

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.schoolapp.databinding.ActivityClassePageBinding
import com.example.schoolapp.ui.addClasse.ui.main.PlaceholderFragment
import com.example.schoolapp.ui.addClasse.ui.main.SectionsPagerAdapter

class ClassePage : AppCompatActivity() {

    private lateinit var binding: ActivityClassePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var id: Int = intent.getIntExtra("id", -1)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.addFragment(PlaceholderFragment(id), "Details")
        sectionsPagerAdapter.addFragment(ClasseStudents(id), "Students")
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

//        val fab: FloatingActionButton = binding.fab
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }
}