package com.fridgefriend


import android.content.Intent
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDateTime

class AddItemActivity : AppCompatActivity() {

    private val foodList = DataSource.foods
    lateinit var food: Food

    //data persistence: initializing db through view model?
    private val viewModel: FridgeViewModel by viewModels {
        FridgeViewModelFactory(
            (application as FridgeApplication).database.foodDao())
    }

    //check if quantity is valid (not 0)
    fun validateQuant(quant: String): Boolean {
        if(quant == "0"){
            return false
        }
        return true

    }

    //check if expiration date is a valid date
    fun validateExpi(expi: String): Boolean {
        if(expi.length < 8){
            return false
        }
        val mm = expi.substring(0, 2)
        val dd = expi.substring(3, 5)
        val yy = expi.substring(6)
        if(expi.substring(2,3) != "/" || expi.substring(5,6) != "/"){
            return false
        }
        if( 0 > mm.toInt() || 12 < mm.toInt()){
            return false
        }
        if( 0 > dd.toInt() || 31 < dd.toInt()){
            return false
        }
        if( 22 > yy.toInt() || 40 < yy.toInt()){
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Set listener to cancel button, result: go back to fridge layout without any changes
        val cancelButton = findViewById<Button>(R.id.button_cancel)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, ListsActivity::class.java)
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
                "Other"-> foodType = R.drawable.fridge
            }

            //data persistence: add to database
            if (validateQuant(foodCount) && validateExpi(foodExpire)) {
                viewModel.addNewFood(
                    foodName,
                    foodCount,
                    foodExpire,
                    radioButton.text.toString()
                )
            }

            if(!validateQuant(foodCount)){
                val text = "Invalid Quantity"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else if(!validateExpi(foodExpire)){
                val text = "Invalid Expiration Date"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else{
                foodList.add(Food(foodType,foodName,foodCount,foodExpire, false, "", ""))
                val intent2 = Intent(this, ListsActivity::class.java)
                startActivity(intent2)
            }


        }
    }

    fun updateImage(view: View){
        val foodImage = findViewById<ImageView>(R.id.food_icon)
        val radioGroup: RadioGroup = findViewById(R.id.type_options)
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
        val radioButton: RadioButton = findViewById(selectedOption)
        when(radioButton.text){
            "Fruit" -> foodImage.setImageResource(R.drawable.fruits)
            "Vegetable" -> foodImage.setImageResource(R.drawable.vegetable)
            "Meat" -> foodImage.setImageResource(R.drawable.proteins)
            "Dairy" -> foodImage.setImageResource(R.drawable.dairy)
            "Other" -> foodImage.setImageResource(R.drawable.fridge)
        }
    }

}