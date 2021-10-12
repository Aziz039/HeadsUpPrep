package com.example.headsupprep

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter (private val celebrities: ArrayList<celebrityDetails>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var cardView: CardView
        var tv_name: TextView
        var tv_taboo1: TextView
        var tv_taboo2: TextView
        var tv_taboo3: TextView
        init {
            cardView = itemView.findViewById(R.id.cardView)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_taboo1 = itemView.findViewById(R.id.tv_taboo1)
            tv_taboo2 = itemView.findViewById(R.id.tv_taboo2)
            tv_taboo3 = itemView.findViewById(R.id.tv_taboo3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = "Name: ${celebrities[position].name}"
        holder.tv_taboo1.text = "${celebrities[position].taboo1}"
        holder.tv_taboo2.text = "${celebrities[position].taboo2}"
        holder.tv_taboo3.text = "${celebrities[position].taboo3}"
    }

    override fun getItemCount() = celebrities.size
}
