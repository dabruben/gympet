package com.dabutu.gympet.Routine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dabutu.gympet.MainActivity
import com.dabutu.gympet.R

class EndRoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_routine)

        val timeTextView: TextView = findViewById(R.id.timeTextView)
        val homeButton: Button = findViewById(R.id.homeButton)
        val congratsGif: ImageView = findViewById(R.id.congratsGif)

        // Set the time from the intent extras
        val time = intent.getStringExtra("time")
        timeTextView.text = "Time: $time"

        // Load the GIF using Glide
        Glide.with(this)
            .asGif()
            .load(R.drawable.hammi)
            .into(congratsGif)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // End the activity and return to the previous one
        }
    }
}
