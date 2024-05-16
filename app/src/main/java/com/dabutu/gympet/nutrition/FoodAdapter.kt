package com.dabutu.gympet.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore





class FoodAdapter(
    private val foodItems: MutableList<FoodItem>,
    private val addItemCallback: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var filteredFoodItems: MutableList<FoodItem> = foodItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = filteredFoodItems[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int = filteredFoodItems.size

    fun updateItems(newFoodItems: MutableList<FoodItem>) {
        foodItems.clear()
        foodItems.addAll(newFoodItems)
        filter("")
    }

    fun filter(query: String) {
        filteredFoodItems = if (query.isEmpty()) {
            foodItems
        } else {
            foodItems.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun updateItem(updatedFoodItem: FoodItem) {
        val index = foodItems.indexOfFirst { it.id == updatedFoodItem.id }
        if (index != -1) {
            foodItems[index] = updatedFoodItem
            filter("")
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.foodNameTextView)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.foodCaloriesTextView)
        private val addButton: Button = itemView.findViewById(R.id.addButton)

        fun bind(foodItem: FoodItem) {
            nameTextView.text = foodItem.name
            caloriesTextView.text = foodItem.calories.toString()
            addButton.setOnClickListener {
                addItemCallback(foodItem)
            }
            if (foodItem.isSelected) {
                addButton.text = "Added"
                addButton.isEnabled = false
            } else {
                addButton.text = "Add"
                addButton.isEnabled = true
            }
        }
    }
}
