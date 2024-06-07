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
import android.widget.ImageView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var totalCaloriesTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var buttonAddPoints: Button
    private lateinit var buttonSubtractPoints: Button
    private lateinit var imageViewInfo: ImageView

    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splashScreen.setKeepOnScreenCondition { false }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("NutritionApp", Context.MODE_PRIVATE)

        // Initialize the TextView for total calories
        totalCaloriesTextView = findViewById(R.id.textViewTotalCalories)
        progressBar = findViewById(R.id.progressBarMascota)
        imageView = findViewById(R.id.imageViewMascota)
        buttonAddPoints = findViewById(R.id.buttonAddPoints)
        buttonSubtractPoints = findViewById(R.id.buttonSubtractPoints)
        imageViewInfo = findViewById(R.id.imageViewInfo)

        // Load and display the total calories
        val totalCalories = sharedPreferences.getInt("totalCalories", 0)
        updateTotalCaloriesTextView(totalCalories)

        // Load the current level
        level = sharedPreferences.getInt("level", 0)
        updateMascotaImage()

        // Setup bottom navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_nutrition -> {
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

        // Load and set the progress
        val progress = sharedPreferences.getInt("progress", 0)
        progressBar.progress = progress

        // Set up button to add points
        buttonAddPoints.setOnClickListener {
            addTrainingPoints()
        }

        // Set up button to subtract points
        buttonSubtractPoints.setOnClickListener {
            subtractTrainingPoints()
        }

        // Set up button to show/hide info image
        val buttonShowImage: Button = findViewById(R.id.buttonShowImage)
        buttonShowImage.setOnClickListener {
            if (imageViewInfo.visibility == ImageView.GONE) {
                imageViewInfo.visibility = ImageView.VISIBLE
            } else {
                imageViewInfo.visibility = ImageView.GONE
            }
        }
    }

    private fun updateTotalCaloriesTextView(calories: Int) {
        totalCaloriesTextView.text = "Total Calories: $calories"
    }

    private fun addTrainingPoints() {
        var progress = progressBar.progress
        progress += 25
        if (progress >= 100) {
            progress = 0
            levelUp()
        }
        progressBar.progress = progress
        sharedPreferences.edit().putInt("progress", progress).apply()
    }

    private fun subtractTrainingPoints() {
        var progress = progressBar.progress
        progress -= 25
        if (progress < 0) {
            progress = 75
            levelDown()
        }
        progressBar.progress = progress
        sharedPreferences.edit().putInt("progress", progress).apply()
    }

    private fun levelUp() {
        if (level < 5) {
            level++
            updateMascotaImage()
            sharedPreferences.edit().putInt("level", level).apply()
        }
    }

    private fun levelDown() {
        if (level > 0) {
            level--
            updateMascotaImage()
            sharedPreferences.edit().putInt("level", level).apply()
        }
    }

    private fun updateMascotaImage() {
        val imageResource = when (level) {
            0 -> R.drawable.ham0
            1 -> R.drawable.ham1
            2 -> R.drawable.ham2
            3 -> R.drawable.ham3
            4 -> R.drawable.ham4
            5 -> R.drawable.ham5
            else -> R.drawable.ham0
        }
        imageView.setImageResource(imageResource)
    }
}