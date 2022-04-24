package com.example.schoolapp.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.R
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.data.Note
import com.example.schoolapp.data.StudentWithclass
import com.example.schoolapp.ui.Exam.IList

class NoteAdapter(private var listener: IList) : ListAdapter<StudentWithclass, NoteAdapter.NoteViewHolder>(
    NotesComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  NoteViewHolder{
        return NoteViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, listener)
    }
    class OnClickListener(val clickListener: (studentWithclass: StudentWithclass) -> Unit) {
        fun onClick(studentWithclass: StudentWithclass) = clickListener(studentWithclass)
    }
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newClasseActivityRequestCode = 1
        private val name: TextView = itemView.findViewById(R.id.name)
        private val note: EditText = itemView.findViewById(R.id.note)
        fun bind(studentWithclass: StudentWithclass?, listener: IList) {
            name.text = studentWithclass!!.student.name
            note.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    listener.addToList(s.toString())

                }
            })
        }

        companion object {
            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_view_design, parent, false)
                return NoteViewHolder(view)
            }
        }
    }

    class NotesComparator : DiffUtil.ItemCallback<StudentWithclass>() {
        override fun areItemsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem === newItem
//            return false
        }

        override fun areContentsTheSame(oldItem: StudentWithclass, newItem: StudentWithclass): Boolean {
            return oldItem.classRoom_Student.csid == newItem.classRoom_Student.csid
//            return false
        }
    }
}