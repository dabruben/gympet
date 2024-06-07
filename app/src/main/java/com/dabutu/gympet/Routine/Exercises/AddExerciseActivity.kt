package com.dabutu.gympet.Routine.Exercises

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore

class AddExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseNameEditText: EditText
    private lateinit var exerciseInstructionsEditText: EditText
    private lateinit var exerciseBodypartSpinner: Spinner
    private lateinit var saveExerciseButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        exerciseNameEditText = findViewById(R.id.exerciseNameEditText)
        exerciseInstructionsEditText = findViewById(R.id.exerciseInstructionsEditText)
        exerciseBodypartSpinner = findViewById(R.id.exerciseBodypartSpinner)
        saveExerciseButton = findViewById(R.id.saveExerciseButton)

        val bodyparts = listOf("back", "chest", "legs", "biceps", "triceps", "shoulders", "abs")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyparts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        exerciseBodypartSpinner.adapter = adapter

        saveExerciseButton.setOnClickListener {
            val exerciseName = exerciseNameEditText.text.toString()
            val exerciseInstructions = exerciseInstructionsEditText.text.toString()
            val exerciseBodypart = exerciseBodypartSpinner.selectedItem.toString()
            if (exerciseName.isNotEmpty() && exerciseInstructions.isNotEmpty() && exerciseBodypart.isNotEmpty()) {
                saveExerciseToFirestore(exerciseName, exerciseInstructions, exerciseBodypart)
            } else {
                Toast.makeText(this, "Please provide exercise name, instructions, and body part", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveExerciseToFirestore(name: String, instructions: String, bodypart: String) {
        val newExercise = hashMapOf(
            "name" to name,
            "instructions" to instructions,
            "sets" to listOf(4),
            "reps" to mutableListOf(12),
            "bodypart" to bodypart
        )

        db.collection("exercises")
            .add(newExercise)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Exercise added with ID: ${documentReference.id}", Toast.LENGTH_SHORT).show()
                finish()  // Close the activity and return to the previous one
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding exercise: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
