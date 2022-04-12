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
import com.example.schoolapp.ui.addClasse.AddClassePage
import com.example.schoolapp.viewModels.ClasseStudentModelFactory
import com.example.schoolapp.viewModels.ClasseStudentViewModel

class ClasseAdapter(private val onClickListener: OnClickListener) : ListAdapter<Classe, ClasseAdapter.WordViewHolder>(WordsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onClickListener)
    }
    class OnClickListener(val clickListener: (classe: Classe) -> Unit) {
        fun onClick(classe: Classe) = clickListener(classe)
    }
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newClasseActivityRequestCode = 1
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        fun bind(classe: Classe?, onClickListener: OnClickListener) {
            image.isVisible = false
            wordItemView.text = classe!!.name
            card.setOnClickListener { view ->
//                goToClassRoom(classe.cid, view)
                onClickListener.onClick(classe)
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view_design, parent, false)
                return WordViewHolder(view)
            }
        }
        private fun goToClassRoom(id: Int, v: View){
            var intent:Intent = Intent(v.context, AddClassePage::class.java)
            intent.putExtra("id", id)
            val origin = v.context as Activity
            origin.startActivityForResult(intent, newClasseActivityRequestCode)
        }

    }

    class WordsComparator : DiffUtil.ItemCallback<Classe>() {
        override fun areItemsTheSame(oldItem: Classe, newItem: Classe): Boolean {
            return oldItem === newItem
//            return false
        }

        override fun areContentsTheSame(oldItem: Classe, newItem: Classe): Boolean {
            return oldItem.cid == newItem.cid
//            return false
        }
    }


}