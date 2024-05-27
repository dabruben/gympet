package com.dabutu.gympet.nutrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R





class AddedFoodAdapter(
    private val selectedFoodItems: MutableList<FoodItem>,
    private val removeItemCallback: (FoodItem) -> Unit
) : RecyclerView.Adapter<AddedFoodAdapter.SelectedFoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selected_food, parent, false)
        return SelectedFoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectedFoodViewHolder, position: Int) {
        val selectedFoodItem = selectedFoodItems[position]
        holder.bind(selectedFoodItem)
    }

    override fun getItemCount(): Int = selectedFoodItems.size

    fun addItem(foodItem: FoodItem) {
        selectedFoodItems.add(foodItem)
        notifyItemInserted(selectedFoodItems.size - 1)
    }

    fun removeItem(foodItem: FoodItem) {
        val position = selectedFoodItems.indexOf(foodItem)
        if (position != -1) {
            selectedFoodItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItems(newSelectedFoodItems: MutableList<FoodItem>) {
        selectedFoodItems.clear()
        selectedFoodItems.addAll(newSelectedFoodItems)
        notifyDataSetChanged()
    }

    inner class SelectedFoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.selectedFoodNameTextView)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.selectedFoodCaloriesTextView)
        private val removeButton: Button = itemView.findViewById(R.id.removeButton)

        fun bind(selectedFoodItem: FoodItem) {
            nameTextView.text = selectedFoodItem.name
            caloriesTextView.text = selectedFoodItem.calories.toString()
            removeButton.setOnClickListener {
                removeItemCallback(selectedFoodItem)
            }
        }
    }
}
