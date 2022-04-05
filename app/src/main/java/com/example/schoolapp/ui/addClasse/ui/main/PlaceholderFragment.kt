package com.example.schoolapp.ui.addClasse.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.Classe
import com.example.schoolapp.databinding.FragmentClassePageBinding
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(_id0: Int) : Fragment() {
    private val classeViewModel: ClasseViewModel by viewModels {
        WordViewModelFactory((activity?.application as MainApp).repositoryClasse)
    }
    var id0: Int = _id0
    private lateinit var name: EditText
    private lateinit var grade: EditText
    private lateinit var date: EditText
    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentClassePageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClassePageBinding.inflate(inflater, container, false)
        val root = binding.root

        name = binding.name
        grade = binding.grade
        date = binding.date
        val button = binding.buttonSave
        if (id0 == -1) {
            button.text = "Save"
        }
        else {
            button.text = "Update"
        }
        initClassRoom()
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(name.text) || TextUtils.isEmpty(grade.text) || TextUtils.isEmpty(date.text) ) {
                activity!!.setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name0 = name.text.toString()
                val grade0 = grade.text.toString()
                val date0 = date.text.toString()
                val array0 =  ArrayList<String>()
                array0.add(id0.toString())
                array0.add(name0)
                array0.add(grade0)
                array0.add(date0)
                replyIntent.putExtra(AddClassePage.EXTRA_REPLY, array0)
                activity!!.setResult(Activity.RESULT_OK, replyIntent)
            }
            activity!!.finish()
        }
        return root
    }
    private fun initClassRoom() {
        if (id0 != -1) {
//            var  classe: Classe = classeViewModel.classById(id0)
//            name.setText(classe.name.toString())
//            grade.setText(classe.grade.toString())
//            date.setText(classe.date.toString())

        }
    }
    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment(-1).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}