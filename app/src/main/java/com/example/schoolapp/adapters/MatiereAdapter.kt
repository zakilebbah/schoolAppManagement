package com.example.schoolapp.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolapp.MainApp
import com.example.schoolapp.R
import com.example.schoolapp.data.Classe
import com.example.schoolapp.data.Matiere
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel

class MatiereAdapter(private val onClickListener: OnClickListener) : ListAdapter<Matiere, MatiereAdapter.WordViewHolder>(WordsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener)
    }
    class OnClickListener(val clickListener: (matiere: Matiere) -> Unit) {
        fun onClick(matiere: Matiere) = clickListener(matiere)
    }
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newClasseActivityRequestCode = 1
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        fun bind(matiere: Matiere?, onClickListener: OnClickListener) {
            image.isVisible = false
            wordItemView.text = matiere!!.name
            card.setOnClickListener { view ->
//                goToClassRoom(classe.cid, view)
                onClickListener.onClick(matiere)
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_design, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Matiere>() {
        override fun areItemsTheSame(oldItem: Matiere, newItem: Matiere): Boolean {
            return oldItem === newItem
//            return false
        }

        override fun areContentsTheSame(oldItem: Matiere, newItem: Matiere): Boolean {
            return oldItem.Mid == newItem.Mid
//            return false
        }
    }


}