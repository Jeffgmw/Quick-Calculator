package com.example.quickcalculator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcalculator.R
import com.example.quickcalculator.model.HistoryItem

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem.toString())
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expressionTextView: TextView = itemView.findViewById(R.id.bvExpression)
        private val resultTextView: TextView = itemView.findViewById(R.id.bvResult)

        fun bind(historyItem: String) {
            val parts = historyItem.split("=")
            if (parts.size == 2) {
                expressionTextView.text = parts[0].trim()
                resultTextView.text = parts[1].trim()
            }
        }
    }
}
