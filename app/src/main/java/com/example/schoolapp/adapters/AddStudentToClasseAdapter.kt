package com.example.schoolapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.R
import com.example.schoolapp.data.Student
import com.example.schoolapp.data.StudentWithclass
import com.example.schoolapp.ui.addStudent.AddStudentPage
import com.example.schoolapp.viewModels.AttendanceViewModel
import com.example.schoolapp.viewModels.ClasseStudentViewModel

class AddStudentToClasseAdapter(private val onClickListener: AddStudentToClasseAdapter.OnClickListener,
                                private val classeViewModel: ClasseStudentViewModel, cid: Int
): ListAdapter<Student, AddStudentToClasseAdapter.StudentViewHolder>(StudentAdapter.StudentComparator()) {
    private val  classeViewModel0: ClasseStudentViewModel = classeViewModel
    private val cid0: Int = cid
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddStudentToClasseAdapter.StudentViewHolder {
        return AddStudentToClasseAdapter.StudentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddStudentToClasseAdapter.StudentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener,classeViewModel0, cid0 )
    }
    class OnClickListener(val clickListener: (student: Student, type: Int) -> Unit) {
        fun onClick(student: Student, type: Int) = clickListener(student, type)
    }
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox1)
        fun bind(student: Student?, onClickListener: OnClickListener,
                 classeViewModel0: ClasseStudentViewModel, cid0: Int) {
            wordItemView.text = student!!.name + " " + student!!.prenom
            if (classeViewModel0.loadCidSid(cid0, student!!.sid) != null) {
                checkBox.isChecked = true
            }
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {

                    onClickListener.onClick(student, 0)
                }
                else {
                    val student0: Student = student
                    onClickListener.onClick(student0, -1)
                }
            }


        }

        companion object {
            fun create(parent: ViewGroup): AddStudentToClasseAdapter.StudentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_add_student_classe, parent, false)
                return AddStudentToClasseAdapter.StudentViewHolder(view)
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

    class StudentComparator : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.sid == newItem.sid
        }
    }
}