package com.dabutu.gympet.Routine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dabutu.gympet.MainActivity
import com.dabutu.gympet.R
import com.dabutu.gympet.nutrition.NutritionScreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoutineActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RoutineAdapter
    private val routineList = ArrayList<Routine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RoutineAdapter(routineList)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, NewRoutineActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_ROUTINE)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navegar a MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_nutrition -> {
                    // Navegar a NutritionScreen
                    val intent = Intent(this, NutritionScreen::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_routine -> {
                    // La rutina actual ya está activa
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ROUTINE && resultCode == RESULT_OK) {
            val newRoutine = data?.getSerializableExtra("newRoutine") as? Routine
            newRoutine?.let {
                routineList.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_ROUTINE = 1
    }
}
