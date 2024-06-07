package com.dabutu.gympet.Routine.Exercises

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore

class ExercisesActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseDescAdapter
    private lateinit var addExerciseButton: Button
    private val exercisesList = ArrayList<ExerciseDesc>()
    private val filteredExercises = ArrayList<ExerciseDesc>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)

        searchEditText = findViewById(R.id.searchExerciseEditText)
        exercisesRecyclerView = findViewById(R.id.exercisesRecyclerView)
        addExerciseButton = findViewById(R.id.addExerciseButton)

        exerciseAdapter = ExerciseDescAdapter(filteredExercises) { exercise ->
            deleteExercise(exercise)
        }
        exercisesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExercisesActivity)
            adapter = exerciseAdapter
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterExercises(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        addExerciseButton.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivity(intent)
        }

        loadExercisesFromFirestore()
    }

    private fun loadExercisesFromFirestore() {
        db.collection("exercises")
            .get()
            .addOnSuccessListener { documents ->
                exercisesList.clear()
                for (document in documents) {
                    val exercise = document.toObject(ExerciseDesc::class.java).copy(id = document.id)
                    exercisesList.add(exercise)
                }
                // Initially display all exercises
                filteredExercises.clear()
                filteredExercises.addAll(exercisesList)
                exerciseAdapter.updateExercises(filteredExercises)
            }
            .addOnFailureListener { exception ->
                // Handle the error
                Toast.makeText(this, "Error loading exercises: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterExercises(query: String) {
        val filteredList = exercisesList.filter { it.name.contains(query, ignoreCase = true) }
        filteredExercises.clear()
        filteredExercises.addAll(filteredList)
        exerciseAdapter.updateExercises(filteredExercises)
    }

    private fun deleteExercise(exercise: ExerciseDesc) {
        db.collection("exercises").document(exercise.id)
            .delete()
            .addOnSuccessListener {
                exercisesList.remove(exercise)
                filterExercises(searchEditText.text.toString())
                Toast.makeText(this, "Exercise deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error deleting exercise: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
