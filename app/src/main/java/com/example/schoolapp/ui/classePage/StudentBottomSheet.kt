package com.example.schoolapp.ui.classePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.schoolapp.R
import com.example.schoolapp.ui.Exam.AveragePage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StudentBottomSheet(private  var sid: Int, private var name: String) : BottomSheetDialogFragment() {
    private var note : LinearLayout? = null
    private var average : LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var _binding = inflater.inflate(R.layout.student_model_sheet, container, false)
        average = _binding.findViewById(R.id.average)
        average!!.setOnClickListener {
            var intent = Intent(context, AveragePage::class.java)
            intent.putExtra("sid", sid)
            intent.putExtra("student_name", name)
            startActivity(intent)
        }
        return _binding
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}