package com.dabutu.gympet.Routine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class NewRoutineActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var titleEditText: EditText
    private lateinit var nameEditText: AutoCompleteTextView
    private lateinit var addExerciseButton: Button
    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exercisesList: ArrayList<Exercise> = arrayListOf(
        Exercise("Push-ups", "Push-ups exercise description",  listOf(3), mutableListOf(10, 15, 20)),
        Exercise("Squats", "Squats exercise description",  listOf(4), mutableListOf(12, 15, 18, 20)),
        Exercise("Plank", "Plank exercise description",  listOf(3), mutableListOf(30, 45, 60)), // Time in seconds
        Exercise("Lunges", "Lunges exercise description",  listOf(3), mutableListOf(12, 12, 12)),
        Exercise("Burpees", "Burpees exercise description", listOf(3), mutableListOf(10, 12, 15))
    )
    private val selectedExercises: ArrayList<Exercise> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_routine)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        titleEditText = findViewById(R.id.titleEditText)
        nameEditText = findViewById(R.id.nameEditText)
        addExerciseButton = findViewById(R.id.addExerciseButton)
        exercisesRecyclerView = findViewById(R.id.exercisesRecyclerView)

        exerciseAdapter = ExerciseAdapter(selectedExercises)
        exercisesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NewRoutineActivity)
            adapter = exerciseAdapter
        }

        val exerciseNames = exercisesList.map { it.name }
        val autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, exerciseNames)
        nameEditText.setAdapter(autoCompleteAdapter)

        addExerciseButton.setOnClickListener {
            val selectedExerciseName = nameEditText.text.toString()
            if (selectedExerciseName.isNotEmpty()) {
                val defaultSeries = 4
                val defaultReps = 12
                val newExercise = Exercise(selectedExerciseName, "Default description",  List(defaultSeries) { defaultSeries }, MutableList(defaultSeries) { defaultReps })
                selectedExercises.add(newExercise)
                exerciseAdapter.notifyDataSetChanged()
                nameEditText.text.clear()  // Clear the text after adding
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_routine, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save_routine -> {
                saveRoutine()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveRoutine() {
        val title = titleEditText.text.toString()
        val newRoutine = Routine(title, selectedExercises.size)
        newRoutine.exercises = selectedExercises

        // Passing the new routine back to RoutineActivity
        val resultIntent = Intent()
        resultIntent.putExtra("newRoutine", newRoutine)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
