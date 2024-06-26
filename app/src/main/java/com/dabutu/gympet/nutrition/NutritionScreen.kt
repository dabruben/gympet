package com.dabutu.gympet.nutrition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.MainActivity
import com.dabutu.gympet.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import android.content.SharedPreferences

class NutritionScreen : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var addedFoodsRecyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var addFoodButton: Button
    private lateinit var totalCaloriesDisplayTextView: TextView

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var addedFoodAdapter: AddedFoodAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var totalCalories = 0
    private val db = FirebaseFirestore.getInstance()
    private val selectedFoodItems = mutableListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NutritionApp", Context.MODE_PRIVATE)

        // Load total calories from SharedPreferences
        totalCalories = sharedPreferences.getInt("totalCalories", 0)

        setupViews()
        setupAdapters()
        setupSearch()
        setupBottomNavigation()
        updateTotalCaloriesTextView()

        addFoodButton.setOnClickListener {
            openAddFoodActivity()
        }
    }

    private fun setupViews() {
        searchEditText = findViewById(R.id.searchEditText)
        totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView)
        foodsRecyclerView = findViewById(R.id.foodsRecyclerView)
        addedFoodsRecyclerView = findViewById(R.id.addedFoodsRecyclerView)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        addFoodButton = findViewById(R.id.addFoodButton)
        totalCaloriesDisplayTextView = findViewById(R.id.textViewTotalCalories)
    }

    private fun setupAdapters() {
        loadFoodItems { foodItems ->
            foodAdapter = FoodAdapter(foodItems.toMutableList()) { item ->
                if (!selectedFoodItems.contains(item)) {
                    addFoodToSelectedList(item)
                    updateCalories(item.calories)
                }
            }

            foodsRecyclerView.adapter = foodAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(this)
        }

        loadSelectedFoodItems { selectedFoodItems ->
            this.selectedFoodItems.addAll(selectedFoodItems)
            addedFoodAdapter = AddedFoodAdapter(selectedFoodItems.toMutableList()) { item ->
                removeFoodFromSelectedList(item)
                updateCalories(-item.calories)
            }

            addedFoodsRecyclerView.adapter = addedFoodAdapter
            addedFoodsRecyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                foodAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateToHome()
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.nav_nutrition
    }

    private fun updateCalories(calories: Int) {
        totalCalories += calories
        updateTotalCaloriesTextView()
        saveTotalCalories()
    }

    private fun updateTotalCaloriesTextView() {
        totalCaloriesTextView.text = "Total Calories: $totalCalories"
        totalCaloriesDisplayTextView.text = "Total Calories: $totalCalories"
    }

    private fun saveTotalCalories() {
        with(sharedPreferences.edit()) {
            putInt("totalCalories", totalCalories)
            apply()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun loadFoodItems(callback: (MutableList<FoodItem>) -> Unit) {
        db.collection("foodItems")
            .get()
            .addOnSuccessListener { result ->
                val foodItems = result.map { document ->
                    document.toObject(FoodItem::class.java)
                }.toMutableList()
                callback(foodItems)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun loadSelectedFoodItems(callback: (MutableList<FoodItem>) -> Unit) {
        db.collection("foodItems")
            .whereEqualTo("isSelected", true)
            .get()
            .addOnSuccessListener { result ->
                val selectedFoodItems = result.map { document ->
                    document.toObject(FoodItem::class.java)
                }.toMutableList()
                callback(selectedFoodItems)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun addFoodToSelectedList(foodItem: FoodItem) {
        db.collection("foodItems")
            .document(foodItem.id)
            .update("isSelected", true)
            .addOnSuccessListener {
                foodItem.isSelected = true
                selectedFoodItems.add(foodItem)
                addedFoodAdapter.addItem(foodItem)
                foodAdapter.updateItem(foodItem)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun removeFoodFromSelectedList(foodItem: FoodItem) {
        db.collection("foodItems")
            .document(foodItem.id)
            .update("isSelected", false)
            .addOnSuccessListener {
                foodItem.isSelected = false
                selectedFoodItems.remove(foodItem)
                addedFoodAdapter.removeItem(foodItem)
                foodAdapter.updateItem(foodItem)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun openAddFoodActivity() {
        val intent = Intent(this, AddFoodActivity::class.java)
        startActivityForResult(intent, ADD_FOOD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_FOOD_REQUEST_CODE && resultCode == RESULT_OK) {
            val calories = data?.getIntExtra("calories", 0) ?: 0
            updateCalories(calories)
            loadFoodItems { foodItems ->
                foodAdapter.updateItems(foodItems.toMutableList())
            }
            loadSelectedFoodItems { selectedFoodItems ->
                addedFoodAdapter.updateItems(selectedFoodItems.toMutableList())
            }
        }
    }

    companion object {
        private const val ADD_FOOD_REQUEST_CODE = 1
    }
}
