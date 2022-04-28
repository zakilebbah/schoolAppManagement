package com.example.schoolapp.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.*
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.ui.classePage.StudentBottomSheet
import com.example.schoolapp.viewModels.AttendanceViewModel
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel
import com.example.schoolapp.viewModels.ClasseViewModel

class StudentClasseAdapter(private val onClickListener: OnClickListener, private val onClickListener2: OnClickListener2, date: String): ListAdapter<StudentWithclass, StudentClasseAdapter.StudentClasseViewHolder>(StudentComparator()) {
//    private val classeStudentsViewModel: ClasseStudentViewModel by  {
//        ClasseStudentModelFactory((activity as MainApp).repositoryClasseStudent)
//    }
    private val date0: String = date
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentClasseAdapter.StudentClasseViewHolder {
        return StudentClasseAdapter.StudentClasseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentClasseAdapter.StudentClasseViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, onClickListener2, date0)
    }
    class OnClickListener(val clickListener: (student: StudentWithclass) -> Unit) {
        fun onClick(student: StudentWithclass) = clickListener(student)
    }
    class OnClickListener2(val clickListener: (student: StudentWithclass, status: Int) -> Unit) {
        fun onClick(student: StudentWithclass, status: Int) = clickListener(student, status)
    }
    class StudentClasseViewHolder(itemView: View, ) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        private val button: ImageView = itemView.findViewById(R.id.person)
        private val note: TextView = itemView.findViewById(R.id.note)


        fun bind(student: StudentWithclass?, onClickListener: OnClickListener, onClickListener2: OnClickListener2, date0: String) {
            wordItemView.text = student!!.student.name + " " + student!!.student.prenom
            card.setOnClickListener { view ->
//                goToStudent(student.sid, view)
                onClickListener.onClick(student)

            }
            if (student.student.note != null) {
                note.text = "Moy: " + student.student.note.toString()
            }
             if (student.student.attendance != null && student.student.attendance == 0) {
              //  button.setBackgroundColor(Color.RED)
                button.setImageResource(R.drawable.ic_baseline_person_red_24)

//                student.classRoom_Student.attendance = 1
            }
            else if (student.student.attendance != null && student.student.attendance == 1) {
               // button.setBackgroundColor(Color.GREEN)
                button.setImageResource(R.drawable.ic_baseline_person_green_24)
            }
            button.setOnClickListener { view ->
                if (student.student.attendance == 0) {
                   student.student.attendance = 1
                   button.setImageResource(R.drawable.ic_baseline_person_green_24)
                   onClickListener2.onClick(student, 1)
                }
                else if (student.student.attendance == 1 || student.student.attendance == null) {
                    student.student.attendance = 0

                    button.setImageResource(R.drawable.ic_baseline_person_red_24)

                    onClickListener2.onClick(student, 0)
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): StudentClasseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_attendence_view, parent, false)
                return StudentClasseViewHolder(view)
            }
        }
        private fun goToStudent(id: Int, v: View){
            val student00 = LinkedHashMap<String, String>()
            Log.d("TEST TEST", student00["nom"].toString())
            val intent = Intent(v.context, AddStudentPage::class.java)
            intent.putExtra("id", id);
            v.context.startActivity(intent)
        }
    }

    class StudentComparator : DiffUtil.ItemCallback<StudentWithclass>() {
        override fun areItemsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem.student.sid == newItem.student.sid
        }
    }
}