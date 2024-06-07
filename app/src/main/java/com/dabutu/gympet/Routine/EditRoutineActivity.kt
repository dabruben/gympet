package com.dabutu.gympet.Routine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore

class EditRoutineActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var titleEditText: EditText
    private lateinit var nameEditText: AutoCompleteTextView
    private lateinit var bodypartSpinner: Spinner
    private lateinit var addExerciseButton: Button
    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: EditRoutineAdapter
    private val exercisesList = ArrayList<Exercise>()
    private val selectedExercises: ArrayList<Exercise> = arrayListOf()
    private val db = FirebaseFirestore.getInstance()
    private var routineId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        titleEditText = findViewById(R.id.titleEditText)
        nameEditText = findViewById(R.id.nameEditText)
        bodypartSpinner = findViewById(R.id.bodypartSpinner)
        addExerciseButton = findViewById(R.id.addExerciseButton)
        exercisesRecyclerView = findViewById(R.id.exercisesRecyclerView)

        val bodyparts = listOf("back", "chest", "legs", "biceps", "triceps", "shoulders", "abs")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bodyparts)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bodypartSpinner.adapter = spinnerAdapter

        exerciseAdapter = EditRoutineAdapter(selectedExercises)
        exercisesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EditRoutineActivity)
            adapter = exerciseAdapter
        }

        addExerciseButton.setOnClickListener {
            val selectedExerciseName = nameEditText.text.toString()
            val selectedBodypart = bodypartSpinner.selectedItem.toString()
            if (selectedExerciseName.isNotEmpty()) {
                val newExercise = exercisesList.firstOrNull { it.name == selectedExerciseName }?.copy(
                    bodyPart = selectedBodypart
                ) ?: Exercise(selectedExerciseName, "Default instructions", selectedBodypart)
                selectedExercises.add(newExercise)
                exerciseAdapter.notifyDataSetChanged()
                nameEditText.text.clear()
            }
        }

        val routineName = intent.getStringExtra("routineName")
        routineName?.let {
            loadRoutineByName(it)
        }

        loadExercisesFromFirestore()
    }

    private fun loadRoutineByName(routineName: String) {
        db.collection("routines")
            .whereEqualTo("title", routineName)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 1) {
                    val routine = documents.first().toObject(Routine::class.java)
                    titleEditText.setText(routine.title)
                    selectedExercises.addAll(routine.exercises)
                    exerciseAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    private fun loadExercisesFromFirestore() {
        db.collection("exercises")
            .get()
            .addOnSuccessListener { documents ->
                exercisesList.clear()
                for (document in documents) {
                    val exercise = document.toObject(Exercise::class.java)
                    exercisesList.add(exercise)
                }
                val exerciseNames = exercisesList.map { it.name }
                val autoCompleteAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, exerciseNames)
                nameEditText.setAdapter(autoCompleteAdapter)
            }
            .addOnFailureListener { exception ->
                // Handle the error
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
        val updatedRoutine = Routine(title, selectedExercises.size, selectedExercises)

        // Buscar si la rutina ya existe
        db.collection("routines")
            .whereEqualTo("title", title)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 1) {
                    // Si existe, actualizar la rutina existente
                    val documentId = documents.first().id
                    db.collection("routines").document(documentId)
                        .set(updatedRoutine)
                        .addOnSuccessListener {
                            println("Routine updated with ID: $documentId")
                            // Enviar los datos de vuelta a la actividad anterior
                            val resultIntent = Intent()
                            resultIntent.putExtra("updatedRoutine", updatedRoutine)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            println("Error updating routine: $e")
                        }
                } else {
                    // Si no existe, crear una nueva rutina
                    db.collection("routines")
                        .add(updatedRoutine)
                        .addOnSuccessListener { documentReference ->
                            println("Routine added with ID: ${documentReference.id}")
                            // Enviar los datos de vuelta a la actividad anterior
                            val resultIntent = Intent()
                            resultIntent.putExtra("newRoutine", updatedRoutine)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            println("Error adding routine: $e")
                        }
                }
            }
            .addOnFailureListener { e ->
                println("Error finding routine: $e")
            }
    }

    private fun deleteRoutine(routineId: String) {
        db.collection("routines").document(routineId)
            .delete()
            .addOnSuccessListener {
                println("Routine deleted with ID: $routineId")
                finish()
            }
            .addOnFailureListener { e ->
                println("Error deleting routine: $e")
            }
    }
}
