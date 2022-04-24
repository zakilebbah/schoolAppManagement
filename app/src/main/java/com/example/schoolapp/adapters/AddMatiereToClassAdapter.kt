package com.example.schoolapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.R
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addStudent.AddStudentPage

class AddMatiereToClassAdapter(private val onClickListener: AddMatiereToClassAdapter.OnClickListener, mid: Int
): ListAdapter<Matiere, AddMatiereToClassAdapter.MatiereViewHolder>(MatiereAdapter.WordsComparator()) {
    private val mid0: Int = mid
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMatiereToClassAdapter.MatiereViewHolder {
        return AddMatiereToClassAdapter.MatiereViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddMatiereToClassAdapter.MatiereViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, mid0 )
    }
    class OnClickListener(val clickListener: (matiere:Matiere) -> Unit) {
        fun onClick(matiere: Matiere) = clickListener(matiere)
    }
    class MatiereViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox1)
        fun bind(matiere: Matiere?, onClickListener: OnClickListener,
                 cid0: Int) {
            wordItemView.text = matiere!!.name
            card.setOnClickListener { view ->
//                goToStudent(student.sid, view)
                onClickListener.onClick(matiere)
            }
            checkBox.isVisible = false

        }

        companion object {
            fun create(parent: ViewGroup): AddMatiereToClassAdapter.MatiereViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_add_student_classe, parent, false)
                return AddMatiereToClassAdapter.MatiereViewHolder(view)
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