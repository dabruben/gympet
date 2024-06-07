package com.dabutu.gympet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var calendarGrid: GridLayout
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        sharedPreferences = getSharedPreferences("NutritionApp", Context.MODE_PRIVATE)
        calendarGrid = findViewById(R.id.calendarGrid)
        buttonSave = findViewById(R.id.buttonSave)

        // Populate the calendar with ToggleButtons
        for (i in 1..30) {
            val toggleButton = ToggleButton(this).apply {
                textOff = i.toString()
                textOn = "$i \nTrained"
                text = i.toString()
                isChecked = sharedPreferences.getBoolean("day_$i", false)
                setOnCheckedChangeListener { _, isChecked ->
                    sharedPreferences.edit().putBoolean("day_$i", isChecked).apply()
                }
            }
            calendarGrid.addView(toggleButton)
        }

        buttonSave.setOnClickListener {
            saveTrainingDays()
        }
    }

    private fun saveTrainingDays() {
        var points = 0
        for (i in 1..30) {
            if (sharedPreferences.getBoolean("day_$i", false)) {
                points += 5
            }
        }
        val intent = Intent().apply {
            putExtra("trainingPoints", points)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}