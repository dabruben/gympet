package com.dabutu.gympet.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class AddedFoodAdapter(private var items: MutableList<FoodItem>, private val onRemoveClick: (FoodItem) -> Unit) : RecyclerView.Adapter<AddedFoodAdapter.AddedFoodViewHolder>() {

    class AddedFoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodNameTextView: TextView = view.findViewById(R.id.foodNameTextView)
        val caloriesTextView: TextView = view.findViewById(R.id.caloriesTextView)
        val removeButton: Button = view.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_added_food, parent, false)
        return AddedFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddedFoodViewHolder, position: Int) {
        val item = items[position]
        holder.foodNameTextView.text = item.foodName
        holder.caloriesTextView.text = "${item.calories} calories"
        holder.removeButton.setOnClickListener {
            items.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            onRemoveClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: FoodItem) {
        items.add(item)
        notifyItemInserted(items.size - 1) // Notifica que un nuevo item ha sido insertado al final de la lista
    }
}
