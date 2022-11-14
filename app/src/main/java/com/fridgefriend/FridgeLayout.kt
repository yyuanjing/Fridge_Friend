package com.fridgefriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FridgeLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge_layout)

        // Set listener to know if we need to move to the add item activity
        val addButton = findViewById<Button>(R.id.add_item_button)
        addButton.setOnClickListener(){
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        val historyButton = findViewById<Button>(R.id.history_button)
        historyButton.setOnClickListener(){
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        //val adapter = FoodCardAdapter(recyclerViewInterface = null)

        // Setting the Adapter with the recyclerview
        // recyclerview.adapter = adapter

    }

    fun editItem(view : View){
        val intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    fun removeItem(view : View){

    }
}