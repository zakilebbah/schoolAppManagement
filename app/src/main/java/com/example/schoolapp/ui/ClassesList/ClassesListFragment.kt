package com.example.schoolapp.ui.ClassesList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.adapters.ClasseAdapter
import com.example.schoolapp.data.Classe
import com.example.schoolapp.databinding.FragmentHomeBinding
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.ui.classePage.MainClassePage
import com.example.schoolapp.viewModels.ClasseViewModel
import com.example.schoolapp.viewModels.WordViewModelFactory

class ClassesListFragment : Fragment() {
    private val newClasseActivityRequestCode = 1
    private lateinit var classesListViewModel: ClassesListViewModel
    private var _binding: FragmentHomeBinding? = null
    private val classeViewModel: ClasseViewModel by viewModels {
        WordViewModelFactory((activity?.application as MainApp).repositoryClasse)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        val recyclerView: RecyclerView = binding.recyclerview
        val adapter = ClasseAdapter(ClasseAdapter.OnClickListener { classe ->
            val intent = Intent(context, MainClassePage::class.java)
            intent.putExtra("id", classe.cid)
            startActivityForResult(intent, newClasseActivityRequestCode)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        classeViewModel.allWords.observe(this) { classes ->
            // Update the cached copy of the words in the adapter.
            classes.let { adapter.submitList(it) }
        }
        val fab =binding.fab
        fab.setOnClickListener {
            val intent = Intent(context, AddClassePage::class.java)
            intent.putExtra("id",-1)
            startActivityForResult(intent, newClasseActivityRequestCode)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        Log.d("DELETE DELETE", resultCode.toString())
        if (requestCode == newClasseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayListExtra(AddClassePage.EXTRA_REPLY)?.let { reply ->
                if (reply[0].toInt() == -1) {
                    var classe0 = Classe(0, reply[1], reply[2], reply[3])
                    classeViewModel.insert(classe0)
                }
                else {

                    var classe0 = Classe(reply[0].toInt(), reply[1], reply[2], reply[3])
                    classeViewModel.update(classe0)
                }

            }
        } else if (requestCode == newClasseActivityRequestCode && resultCode == -10){
            Log.d("DELETE DELETE", "FDZZZZZZZZZZZZZZZZZZZZZ")
            intentData?.getIntExtra(AddClassePage.EXTRA_REPLY, -1)?.let {reply ->
                classeViewModel.deleteById(reply)
            }
        }
        else {
            null
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}