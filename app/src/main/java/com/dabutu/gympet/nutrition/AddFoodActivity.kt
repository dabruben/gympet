package com.dabutu.gympet.nutrition

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dabutu.gympet.R
import com.google.firebase.firestore.FirebaseFirestore







class AddFoodActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        nameEditText = findViewById(R.id.nameEditText)
        caloriesEditText = findViewById(R.id.caloriesEditText)
        addButton = findViewById(R.id.addButton)
        cancelButton = findViewById(R.id.cancelButton)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val calories = caloriesEditText.text.toString().toIntOrNull()
            if (name.isNotEmpty() && calories != null) {
                addFoodToFirestore(name, calories)
            }
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun addFoodToFirestore(name: String, calories: Int) {
        val id = db.collection("foodItems").document().id
        val newFood = FoodItem(id = id, name = name, calories = calories, isSelected = false)
        db.collection("foodItems")
            .document(id)
            .set(newFood)
            .addOnSuccessListener {
                setResult(Activity.RESULT_OK)
                finish()
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }
}
