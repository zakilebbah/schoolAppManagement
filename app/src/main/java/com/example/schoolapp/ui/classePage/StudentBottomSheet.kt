package com.example.schoolapp.ui.classePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.schoolapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StudentBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.student_model_sheet, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}