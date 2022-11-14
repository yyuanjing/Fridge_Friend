package com.fridgefriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.fridgefriend.R
import org.w3c.dom.Text

class ViewItemActivity : AppCompatActivity() {

    private val data = DataSource.foods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        val extras = intent.extras
        val position = extras?.get("position") as Int

        val currFood = data[position]

        findViewById<TextView>(R.id.nameView).text = currFood.name
        findViewById<ImageView>(R.id.imageView).setImageResource(currFood.imageResourceId)
        findViewById<TextView>(R.id.quantityInput).text = currFood.quantity
        findViewById<TextView>(R.id.expirationInput).text = currFood.expiration_date

        findViewById<Button>(R.id.eatButton).setOnClickListener(){

        }

        findViewById<Button>(R.id.removeButton).setOnClickListener(){

        }

        findViewById<Button>(R.id.recipeButton).setOnClickListener(){

        }

    }
}