package com.fridgefriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set listener to know when to move on to next activity (fridge layout)
        val button = findViewById<Button>(R.id.open_fridge_button)
        button.setOnClickListener(){
            val intent = Intent(this, FridgeLayout::class.java)
            startActivity(intent)
            finish()
        }
    }
}