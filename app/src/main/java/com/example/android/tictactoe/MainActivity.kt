package com.example.android.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        val buttonAbout = findViewById<Button>(R.id.button_about)
        buttonAbout.setOnClickListener {
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.activity_main_layout, AboutFragment())
            fragmentTransaction.setReorderingAllowed(true)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val buttonPlay = findViewById<Button>(R.id.button_play)
        buttonPlay.setOnClickListener {
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.activity_main_layout, PlayGroundFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}


