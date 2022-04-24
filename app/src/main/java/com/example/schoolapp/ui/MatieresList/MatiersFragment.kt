package com.example.schoolapp.ui.MatieresList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.adapters.ClasseAdapter
import com.example.schoolapp.adapters.MatiereAdapter
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.data.Student
import com.example.schoolapp.databinding.FragmentMatieresBinding
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.MatiereViewModelFactory
import com.example.schoolapp.viewModels.MatieresViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory

class MatiersFragment : Fragment() {

    private val matieresViewModel: MatieresViewModel by viewModels {
        MatiereViewModelFactory((activity?.application as MainApp).repositoryMatier)
    }
    private var _binding: FragmentMatieresBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMatieresBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (activity as AppCompatActivity).supportActionBar?.title = "Matiers"
        val recyclerView: RecyclerView = binding.recyclerview
        val adapter = MatiereAdapter(MatiereAdapter.OnClickListener { matiere ->
//            val intent = Intent(context, addMatierePage::class.java)
            val intent = Intent(context, MatierePage::class.java)
            intent.putExtra("id", matiere.Mid)
            resultLauncher.launch(intent)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        matieresViewModel.allMatiers.observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            classes.let { adapter.submitList(it) }
        }
        val fab =binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, addMatierePage::class.java)
            intent.putExtra("id",-1)
            resultLauncher.launch(intent)
        }
        return root
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringArrayListExtra(AddStudentPage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    val matiere0 = Matiere( 0, reply[1], reply[2].toDouble())
                    matieresViewModel.insert(matiere0)
                }
                else {
                    val matiere0 = Matiere( reply[0].toInt(), reply[1], reply[2].toDouble())
                    matieresViewModel.update(matiere0)
                }

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}