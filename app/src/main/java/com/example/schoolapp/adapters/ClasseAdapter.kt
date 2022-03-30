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
import com.example.schoolapp.data.Classe
import com.example.schoolapp.ui.addClasse.AddClassePage

class ClasseAdapter : ListAdapter<Classe, ClasseAdapter.WordViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.name)
        private val card: CardView = itemView.findViewById(R.id.card)
        fun bind(classe: Classe?) {
            wordItemView.text = classe!!.name
            card.setOnClickListener { view ->
                goToClassRoom(classe.cid, view)
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
            val intent = Intent(v.context, AddClassePage::class.java)
            intent.putExtra("id", id);
            v.context.startActivity(intent)
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Classe>() {
        override fun areItemsTheSame(oldItem: Classe, newItem: Classe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Classe, newItem: Classe): Boolean {
            return oldItem.cid == newItem.cid
        }
    }
}