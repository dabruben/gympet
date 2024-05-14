package com.dabutu.gympet.nutrition

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.dabutu.gympet.R

class NutritionScreen : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var addedFoodsRecyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var addedFoodAdapter: AddedFoodAdapter

    private var totalCalories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        searchEditText = findViewById(R.id.searchEditText)
        totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView)
        foodsRecyclerView = findViewById(R.id.foodsRecyclerView)
        addedFoodsRecyclerView = findViewById(R.id.addedFoodsRecyclerView)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val foodItems = loadFoodItems() // Carga de datos ficticios para los alimentos

        foodAdapter = FoodAdapter(foodItems) { item ->
            addedFoodAdapter.addItem(item)
            totalCalories += item.calories
            totalCaloriesTextView.text = "Total Calories: $totalCalories"
        }

        addedFoodAdapter = AddedFoodAdapter(mutableListOf()) { item ->
            totalCalories -= item.calories
            totalCaloriesTextView.text = "Total Calories: $totalCalories"
        }

        foodsRecyclerView.adapter = foodAdapter
        addedFoodsRecyclerView.adapter = addedFoodAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                foodAdapter.items = foodItems.filter { it.foodName.contains(s.toString(), ignoreCase = true) }
                foodAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // Configuración del listener de navegación para el BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateToHome()
                    true
                }
                else -> false
            }
        }

        // Establece el ítem seleccionado en el BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.nav_nutrition
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loadFoodItems(): List<FoodItem> {
        return listOf(
            FoodItem(1, "Apple", 95),
            FoodItem(2, "Banana", 105),
            FoodItem(3, "Cooked Egg", 155)
        )
    }
}
