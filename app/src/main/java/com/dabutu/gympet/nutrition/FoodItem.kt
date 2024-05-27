package com.dabutu.gympet.nutrition

data class FoodItem(
    val id: String = "",
    val name: String = "",
    val calories: Int = 0,
    var isSelected: Boolean = false
)
