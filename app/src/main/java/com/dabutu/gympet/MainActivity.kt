package com.dabutu.gympet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.dabutu.gympet.Routine.RoutineActivity
import com.dabutu.gympet.nutrition.NutritionScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
                    // Este es la pantalla actual, no hace nada o refresca
                    true
                }
                R.id.nav_nutrition -> {
                    // Navegar a NutritionScreen
                    val intent = Intent(this, NutritionScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_routine -> {
                    val intent = Intent(this, RoutineActivity::class.java)
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