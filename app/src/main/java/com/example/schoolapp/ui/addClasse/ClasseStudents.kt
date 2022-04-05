package com.example.schoolapp.ui.addClasse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ClasseAdapter
import com.example.schoolapp.adapters.StudentAdapter
import com.example.schoolapp.data.ClassRoom_Student
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.Student
import com.example.schoolapp.data.StudentWithclass
import com.example.schoolapp.databinding.FragmentClassePageBinding
import com.example.schoolapp.databinding.FragmentClasseStudentsBinding
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClasseStudents.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClasseStudents(_id: Int) : Fragment() {
    private val classeStudentsViewModel: ClasseStudentViewModel by viewModels {
        ClasseStudentModelFactory((activity?.application as MainApp).repositoryClasseStudent)
    }
    private val newClasseActivityRequestCode = 1
    private var param1: String? = null
    private var param2: String? = null
    var id0: Int = _id
    private var _binding: FragmentClasseStudentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClasseStudentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.recyclerview
        val adapter = StudentAdapter(StudentAdapter.OnClickListener { student ->
            val intent = Intent(context, AddStudentPage::class.java)
            intent.putExtra("id", student.sid)
            startActivityForResult(intent, newClasseActivityRequestCode)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        classeStudentsViewModel.allStudents(id0).observe(this) { classeStudents ->
            var students: List<Student> = listOf()
            for (st in classeStudents) {
                students += st.student
            }
            students.let { adapter.submitList(students) }
        }
        val fab =binding.fab
        fab.setOnClickListener {
            Log.d("TEST TEST ", "*************************************")
            val intent = Intent(context, StudentsList::class.java)
            intent.putExtra("cid",id0)
            startActivityForResult(intent, newClasseActivityRequestCode)
        }
        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newClasseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getIntArrayExtra(StudentsList.EXTRA_REPLY)?.let { reply ->
                if (reply[0] != null) {
                    var classeWithStudent = ClassRoom_Student(reply[0], reply[1], reply[2], reply[3])
                    classeStudentsViewModel.insert(classeWithStudent)
                }

            }
        } else {
            Toast.makeText(
                context,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClasseStudents.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClasseStudents(-1).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}