package com.example.schoolapp.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.R
import com.example.schoolapp.data.Examen
import com.example.schoolapp.data.Student
import com.example.schoolapp.ui.addStudent.AddStudentPage
import java.util.*
import kotlin.collections.LinkedHashMap

class ExamAdapter(private val onClickListener: OnClickListener): ListAdapter<Examen, ExamAdapter.ExamViewHolder>(ExamComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        return ExamViewHolder.create(parent)
    }
    var colors: List<String> = listOf("#7ACAFF", "#FF7878")
    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener, position, colors)
    }
    class OnClickListener(val clickListener: (examen: Examen) -> Unit) {
        fun onClick(examen: Examen) = clickListener(examen)
    }
    class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val iconText: TextView = itemView.findViewById(R.id.icon_text)
        private val card: CardView = itemView.findViewById(R.id.card)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(examen: Examen?, onClickListener: OnClickListener, position0: Int, colors: List<String>) {
            wordItemView.text = examen!!.name
            image.isVisible = false
            card.setOnClickListener { view ->
//                goToStudent(student.sid, view)
                onClickListener.onClick(examen)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ExamAdapter.ExamViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_design, parent, false)
                return ExamAdapter.ExamViewHolder(view)
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

    class ExamComparator : DiffUtil.ItemCallback<Examen>() {
        override fun areItemsTheSame(oldItem: Examen, newItem: Examen): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Examen, newItem: Examen): Boolean {
            return oldItem.Eid == newItem.Eid
        }
    }
}