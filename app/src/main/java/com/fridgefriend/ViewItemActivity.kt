package com.fridgefriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fridgefriend.R

class ViewItemActivity : AppCompatActivity() {

    private val data = DataSource.foods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        val extras = intent.extras
        val position = extras?.get("position")
    }
}