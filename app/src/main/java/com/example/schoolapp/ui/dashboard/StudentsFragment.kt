package com.example.schoolapp.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ClasseAdapter
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.data.Student
import com.example.schoolapp.databinding.FragmentStudentsBinding
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.addStudent.AddStudentPage


class StudentsFragment : Fragment() {
    private val newStudentActivityRequestCode = 1
    private var _binding: FragmentStudentsBinding? = null

    private val studentsViewModel: StudentsViewModel by viewModels {
        StudentsViewModelFactory((activity?.application as MainApp).repositoryStudent)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView: RecyclerView = binding.recyclerview
        val adapter = StudentAdapter(StudentAdapter.OnClickListener { student ->
            val intent = Intent(context, AddStudentPage::class.java)
            intent.putExtra("id", student.sid)
            startActivityForResult(intent, newStudentActivityRequestCode)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        studentsViewModel.allWords.observe(this) { students ->
            // Update the cached copy of the words in the adapter.
            students.let { adapter.submitList(it) }
        }
        val fab =binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, AddStudentPage::class.java)
            startActivityForResult(intent, newStudentActivityRequestCode)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newStudentActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val student0 = Student( 0, reply[1], reply[2], reply[3], reply[4], reply[5], reply[6], reply[7])
                    studentsViewModel.insert(student0)
                }
                else {
                    val student0 = Student( reply[0].toInt(), reply[1], reply[2], reply[3], reply[4], reply[5], reply[6], reply[7])
                    studentsViewModel.update(student0)
                }

            }
        }
//        else {
//            Toast.makeText(
//                context,
//                R.string.empty_not_saved,
//                Toast.LENGTH_LONG
//            ).show()
//        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}