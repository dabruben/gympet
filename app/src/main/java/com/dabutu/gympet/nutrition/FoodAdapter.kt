package com.dabutu.gympet.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class FoodAdapter(var items: List<FoodItem>, private val onAddClick: (FoodItem) -> Unit) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodNameTextView: TextView = view.findViewById(R.id.foodNameTextView)
        val caloriesTextView: TextView = view.findViewById(R.id.caloriesTextView)
        val addButton: Button = view.findViewById(R.id.addButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.foodNameTextView.text = item.foodName
        holder.caloriesTextView.text = "${item.calories} calories"
        holder.addButton.setOnClickListener { onAddClick(item) }
    }

    override fun getItemCount(): Int = items.size
}