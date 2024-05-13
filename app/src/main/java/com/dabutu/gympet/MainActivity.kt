package com.dabutu.gympet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonObjetivos = findViewById<Button>(R.id.buttonObjetivos)
        val buttonHorario = findViewById<Button>(R.id.btnEjercicios)
        val buttonNutricion = findViewById<Button>(R.id.buttonNutricion)

        buttonObjetivos.setOnClickListener {
            // Intent para abrir la actividad de Objetivos
        }

        buttonHorario.setOnClickListener {
            // Intent para abrir la actividad de Horario Semanal
        }

        buttonNutricion.setOnClickListener {
            // Intent para abrir la actividad de Nutrición
        }
    }
}
