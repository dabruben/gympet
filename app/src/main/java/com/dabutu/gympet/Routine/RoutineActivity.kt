package com.dabutu.gympet.Routine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.MainActivity
import com.dabutu.gympet.R
import com.dabutu.gympet.Routine.Exercises.ExercisesActivity
import com.dabutu.gympet.nutrition.NutritionScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class RoutineActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoutineAdapter
    private val routineList = ArrayList<Routine>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RoutineAdapter(routineList, this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, NewRoutineActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_ROUTINE)
        }

        val exercisesButton: Button = findViewById(R.id.exercisesButton)
        exercisesButton.setOnClickListener {
            val intent = Intent(this, ExercisesActivity::class.java)
            startActivity(intent)
        }

        val startRoutineButton: Button = findViewById(R.id.startRoutineButton)
        startRoutineButton.setOnClickListener {
            // Aquí puedes añadir la lógica para empezar la rutina
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_nutrition -> {
                    val intent = Intent(this, NutritionScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_routine -> {
                    true
                }
                else -> false
            }
        }

        loadRoutinesFromFirestore()
    }

    private fun loadRoutinesFromFirestore() {
        db.collection("routines")
            .get()
            .addOnSuccessListener { documents ->
                routineList.clear()
                for (document in documents) {
                    val routine = document.toObject(Routine::class.java)
                    routineList.add(routine)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ROUTINE && resultCode == RESULT_OK) {
            val newRoutine = data?.getSerializableExtra("newRoutine") as? Routine
            newRoutine?.let {
                routineList.add(it)
                saveRoutineToFirestore(it)
                adapter.notifyDataSetChanged()
            }
        } else if (requestCode == REQUEST_CODE_EDIT_ROUTINE && resultCode == RESULT_OK) {
            // Handle the result from EditRoutineActivity if necessary
        }
    }

    private fun saveRoutineToFirestore(routine: Routine) {
        db.collection("routines")
            .add(routine)
            .addOnSuccessListener {
                // Routine added successfully
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    companion object {
        const val REQUEST_CODE_ADD_ROUTINE = 1
        const val REQUEST_CODE_EDIT_ROUTINE = 2
    }
}
