package com.dabutu.gympet.Routine

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.R

class StartRoutineActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingAdapter
    private lateinit var exercises: List<ExerciseWithSets>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_routine)

        chronometer = findViewById(R.id.timerTextView)
        recyclerView = findViewById(R.id.exercisesRecyclerView)

        val selectedRoutine = intent.getSerializableExtra("selectedRoutine") as Routine?
        exercises = selectedRoutine?.exercises?.map { ExerciseWithSets(it.name, it.instructions, it.bodyPart, 4) } ?: emptyList()

        adapter = TrainingAdapter(exercises)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()

        val endRoutineButton: Button = findViewById(R.id.endRoutineButton)
        endRoutineButton.setOnClickListener {
            endRoutine()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_start_routine, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_end_routine -> {
                endRoutine()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun endRoutine() {
        chronometer.stop()
        val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
        val intent = Intent(this, EndRoutineActivity::class.java)
        intent.putExtra("time", formatTime(elapsedMillis))
        startActivity(intent)
        finish() // End the activity and return to the previous one
    }

    private fun formatTime(elapsedMillis: Long): String {
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
