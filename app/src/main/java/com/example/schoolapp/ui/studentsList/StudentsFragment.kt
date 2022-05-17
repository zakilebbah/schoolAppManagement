package com.example.schoolapp.ui.studentsList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.data.Student
import com.example.schoolapp.databinding.FragmentStudentsBinding
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
    private var searchText : EditText? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: StudentAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).supportActionBar?.title = "Students"
        recyclerView = binding.recyclerview
        searchText = binding.editsearch
        adapter = StudentAdapter(StudentAdapter.OnClickListener { student ->
            val intent = Intent(context, AddStudentPage::class.java)
            intent.putExtra("id", student.sid)
            resultLauncher.launch(intent)
            })
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        studentsViewModel.allWords.observe(this) { students ->
            students.let { adapter!!.submitList(it) }
        }
        searchText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                Log.d("TEST ", s.toString())
                onSearch(s.toString())
            }

        })
        val fab =binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, AddStudentPage::class.java)
            resultLauncher.launch(intent)
        }
        return root
    }
    fun onSearch(name0: String) {
        adapter = StudentAdapter(StudentAdapter.OnClickListener { student ->
            val intent = Intent(context, AddStudentPage::class.java)
            intent.putExtra("id", student.sid)
            resultLauncher.launch(intent)
//            startActivityForResult(intent, newStudentActivityRequestCode)
        })
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        studentsViewModel.search("%"+ name0 +"%").observe(this) { students ->
            Log.d("TEST ", students.size.toString())
            students.let { adapter!!.submitList(it) }
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val student0 = Student( 0, reply[1], reply[2], reply[3], reply[4], reply[5], reply[6], reply[7], null, null)
                    studentsViewModel.insert(student0)
                }
                else {
                    val student0 = Student( reply[0].toInt(), reply[1], reply[2], reply[3], reply[4], reply[5], reply[6], reply[7], null, null)
                    studentsViewModel.update(student0)
                }

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


