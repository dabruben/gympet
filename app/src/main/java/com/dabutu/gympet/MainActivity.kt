package com.dabutu.gympet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.widget.TextView
import com.dabutu.gympet.nutrition.NutritionScreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var totalCaloriesTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashScreen.setKeepOnScreenCondition { false }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NutritionApp", Context.MODE_PRIVATE)

        // Initialize the TextView for total calories
        totalCaloriesTextView = findViewById(R.id.textViewTotalCalories)

        // Load and display the total calories
        val totalCalories = sharedPreferences.getInt("totalCalories", 0)
        updateTotalCaloriesTextView(totalCalories)

        // Setup bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // This is the current screen, do nothing or refresh
                    true
                }
                R.id.nav_nutrition -> {
                    // Navigate to NutritionScreen
                    val intent = Intent(this, NutritionScreen::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun updateTotalCaloriesTextView(calories: Int) {
        totalCaloriesTextView.text = "Total Calories: $calories"
    }
}
