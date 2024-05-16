package com.dabutu.gympet.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class AddedFoodAdapter(private val addedFoodItems: MutableList<FoodItem>, private val removeItemCallback: (FoodItem) -> Unit) : RecyclerView.Adapter<AddedFoodAdapter.AddedFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_added_food, parent, false)
        return AddedFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddedFoodViewHolder, position: Int) {
        val foodItem = addedFoodItems[position]
        holder.bind(foodItem, removeItemCallback)
    }

    override fun getItemCount(): Int = addedFoodItems.size

    fun addItem(foodItem: FoodItem) {
        addedFoodItems.add(foodItem)
        notifyDataSetChanged()
    }

    fun removeItem(foodItem: FoodItem) {
        addedFoodItems.remove(foodItem)
        notifyDataSetChanged()
    }

    class AddedFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodNameTextView: TextView = itemView.findViewById(R.id.foodNameTextView)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
        private val removeButton: Button = itemView.findViewById(R.id.removeButton)

        fun bind(foodItem: FoodItem, removeItemCallback: (FoodItem) -> Unit) {
            foodNameTextView.text = foodItem.name
            caloriesTextView.text = "Calories: ${foodItem.calories}"
            removeButton.setOnClickListener { removeItemCallback(foodItem) }
        }
    }
}