package com.fridgefriend


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {
    private val foodList = DataSource.foods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Set listener to cancel button, result: go back to fridge layout without any changes
        val button = findViewById<Button>(R.id.button_cancel)
        button.setOnClickListener(){
            val intent = Intent(this, FridgeLayout::class.java)
            startActivity(intent)
        }

        // Set listener to done to take the input from edittext objects and put them in the list
        val doneButton = findViewById<Button>(R.id.button_done)
        doneButton.setOnClickListener(){
            val nameET = findViewById<View>(R.id.input_name) as EditText
            val foodName = nameET.text.toString()

            val quantityET = findViewById<View>(R.id.input_quantity) as EditText
            val foodCount = quantityET.text.toString()

            val expireET = findViewById<View>(R.id.input_exp_date) as EditText
            val foodExpire = expireET.text.toString()

            val radioGroup: RadioGroup = findViewById(R.id.type_options)
            val selectedOption: Int = radioGroup!!.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(selectedOption)

            var foodType = R.drawable.fruits
            when (radioButton.text){
                "Fruit"-> foodType = R.drawable.fruits
                "Vegetable"-> foodType = R.drawable.vegetable
                "Meat"-> foodType = R.drawable.proteins
                "Dairy"-> foodType = R.drawable.dairy
            }

            foodList.add(FoodData(foodType,foodName,foodCount,foodExpire))

            val intent2 = Intent(this,FridgeLayout::class.java)
            startActivity(intent2)
        }

    }
}