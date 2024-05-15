package com.dabutu.gympet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dabutu.gympet.nutrition.NutritionScreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSlpash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenSlpash.setKeepOnScreenCondition { false }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Este es la pantalla actual, no hace nada o refresca
                    true
                }
                R.id.nav_nutrition -> {
                    // Navegar a NutritionScreen
                    val intent = Intent(this, NutritionScreen::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}