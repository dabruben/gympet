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
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = filteredFoodItems[position]
        holder.bind(foodItem, addItemCallback)
    }

    override fun getItemCount(): Int = filteredFoodItems.size

    fun addItem(foodItem: FoodItem) {
        foodItems.add(foodItem)
        filter("")
    }

    fun removeItem(foodItem: FoodItem) {
        val index = foodItems.indexOf(foodItem)
        if (index != -1) {
            foodItems.removeAt(index)
            filter("")
        }
    }

    fun filter(query: String) {
        filteredFoodItems = if (query.isEmpty()) {
            foodItems
        } else {
            foodItems.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun updateItems(newFoodItems: MutableList<FoodItem>) {
        foodItems.clear()
        foodItems.addAll(newFoodItems)
        filter("")
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodNameTextView: TextView = itemView.findViewById(R.id.foodNameTextView)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.caloriesTextView)
        private val addButton: Button = itemView.findViewById(R.id.addButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(foodItem: FoodItem, addItemCallback: (FoodItem) -> Unit) {
            foodNameTextView.text = foodItem.name
            caloriesTextView.text = "Calories: ${foodItem.calories}"
            addButton.setOnClickListener { addItemCallback(foodItem) }
            deleteButton.setOnClickListener { deleteFoodFromFirestore(foodItem) }
        }

        private fun deleteFoodFromFirestore(foodItem: FoodItem) {
            db.collection("foodItems")
                .whereEqualTo("id", foodItem.id)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        db.collection("foodItems").document(document.id).delete()
                    }
                    removeItem(foodItem)
                }
                .addOnFailureListener { exception ->
                    // Handle the error
                }
        }
    }
}
