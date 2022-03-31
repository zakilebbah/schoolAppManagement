package com.example.schoolapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.R
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addStudent.AddStudentPage

class StudentAdapter(private val onClickListener: OnClickListener): ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.StudentViewHolder {
        return StudentAdapter.StudentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener)
    }
    class OnClickListener(val clickListener: (student: Student) -> Unit) {
        fun onClick(student: Student) = clickListener(student)
    }
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        fun bind(student: Student?, onClickListener: OnClickListener) {
            wordItemView.text = student!!.name + " " + student!!.prenom
            card.setOnClickListener { view ->
//                goToStudent(student.sid, view)
                onClickListener.onClick(student)
            }
        }

        companion object {
            fun create(parent: ViewGroup): StudentAdapter.StudentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_design, parent, false)
                return StudentAdapter.StudentViewHolder(view)
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