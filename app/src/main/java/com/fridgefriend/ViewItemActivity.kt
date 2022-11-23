package com.fridgefriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val intent = intent

        val position = intent.getIntExtra("position", 0)
        val currFood = data[position]

        findViewById<TextView>(R.id.item_name_text).text = currFood.name
        findViewById<ImageView>(R.id.icon_image).setImageResource(currFood.imageResourceId)
        findViewById<TextView>(R.id.quantity_input).text = currFood.quantity
        findViewById<TextView>(R.id.expiration_input).text = currFood.expiration_date
        // findViewById<TextView>(R.id.type_input).text = currFood.imageResourceId as String

        findViewById<Button>(R.id.delete_button).setOnClickListener(){

        }

        findViewById<Button>(R.id.recipe_button).setOnClickListener(){

        }

        findViewById<Button>(R.id.edit_button).setOnClickListener(){

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}