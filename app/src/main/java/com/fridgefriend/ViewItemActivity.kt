package com.fridgefriend

import android.content.Intent
import android.net.Uri
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
    private val historyData = DataSource.expired
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
        findViewById<TextView>(R.id.type_input).text = currFood.type
        findViewById<TextView>(R.id.notes_input).text = currFood.notes

        findViewById<Button>(R.id.delete_button).setOnClickListener(){
            val intent = Intent(this, ListsActivity::class.java)
            val moveToHistory = data.removeAt(position)
            historyData.add(moveToHistory)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.recipe_button).setOnClickListener(){
            val queryUrl: Uri = Uri.parse("https://www.google.com/search?q=${currFood.name}+recipes")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            startActivity(intent)
        }

        findViewById<Button>(R.id.edit_button).setOnClickListener(){
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("newItem", false)
            intent.putExtra("position", position)
            startActivity(intent)
            finish()
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