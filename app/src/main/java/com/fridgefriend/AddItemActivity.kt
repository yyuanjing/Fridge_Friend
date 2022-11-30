package com.fridgefriend


import android.content.Intent
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.content.ContextCompat.startActivity
import java.time.LocalDateTime
import java.util.*

class AddItemActivity : AppCompatActivity() {

    private val foodList = DataSource.foods
    lateinit var food: Food

    //data persistence: initializing db through view model?

    private val viewModel: FridgeViewModel by viewModels {
        FridgeViewModelFactory(
            (application as FridgeApplication).database.foodDao())
    }


    // Helper method to check if quantity is valid (bigger than 0)
    private fun validateQuant(quant: String): Boolean {
        if(quant <= "0"){
            return false
        }
        return true
    }

    // Check if expiration date is a valid date
    private fun validateExpDate(view: DatePicker): Boolean {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        if (view.year < year){
            return false
        } else if (view.year == year){
            if (view.month < month) {
                return false
            } else if (view.month == month) {
                if (view.dayOfMonth < day){
                    return false
                }
            }
        }
        return true
    }

    // Check that all relevant information has been filled out
    private fun validateFieldsFilled(name: EditText, quantity: EditText,
                                     radioGroup: RadioGroup): Boolean{
        if(name.text.toString().isEmpty() || quantity.text.toString().isEmpty()
            || radioGroup.checkedRadioButtonId == -1){
            return false
        }
        return true
    }

    // Update all information (used in the case where we're editing an existing item's info)
    private fun updateData(nameET: EditText, radioGroup: RadioGroup, quantityET: EditText,
                           expireET : DatePicker, position: Int, notesET: EditText){
        val title = findViewById<TextView>(R.id.title)
        title.text = "Edit Item"
        val typeId = when(foodList[position].type) {
            "Fruit" -> R.id.option_fruit
            "Vegetable" -> R.id.option_vegetable
            "Meat" -> R.id.option_meat
            "Dairy" -> R.id.option_dairy
            else -> R.id.option_other
        }
        updateImage(typeId)
        nameET.setText(foodList[position].name)
        radioGroup.check(typeId)
        quantityET.setText(foodList[position].quantity)
        val dateTokenizer = StringTokenizer(foodList[position].expiration_date, "/")
        try {
            val month = dateTokenizer.nextToken().toInt() - 1
            val day = dateTokenizer.nextToken().toInt()
            val year = dateTokenizer.nextToken().toInt()
            expireET.init(year, month, day, null)
        } catch (nfe: NumberFormatException){
            val text = "Error loading expiration date"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        notesET.setText(foodList[position].notes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val newItem = intent.getBooleanExtra("newItem", true)
        val position = intent.getIntExtra("position", -1)

        val nameET = findViewById<View>(R.id.input_name) as EditText
        val quantityET = findViewById<View>(R.id.input_quantity) as EditText
        val radioGroup = findViewById<RadioGroup>(R.id.type_options)
        val expireET = findViewById<DatePicker>(R.id.exp_date_date_picker)
        val notesET = findViewById<View>(R.id.notes_input) as EditText

        // Set information if we're editing an existing item's information
        if (!newItem){
            updateData(nameET, radioGroup, quantityET, expireET, position, notesET)
        }

        // Set it so image changes upon changing type of food
        radioGroup.setOnCheckedChangeListener { group, checkedId ->  updateImage(checkedId)}

        // Set listener to done to take the input from edittext objects and put them in the list
        val doneButton = findViewById<Button>(R.id.button_done)
        doneButton.setOnClickListener(){
            if (validateFieldsFilled(nameET, quantityET, radioGroup)){
                val foodName = nameET.text.toString()
                val foodCount = quantityET.text.toString()
                val foodExpire = (expireET.month + 1).toString() + "/" +
                        expireET.dayOfMonth.toString() + "/" + expireET.year.toString()
                val foodNotes = notesET.text.toString()

                val selectedOption: Int = radioGroup!!.checkedRadioButtonId
                val radioButton: RadioButton = findViewById(selectedOption)
                val foodTypeString = radioButton.text.toString()

                var foodType = R.drawable.fruits
                when (foodTypeString){
                    "Fruit"-> foodType = R.drawable.fruits
                    "Vegetable"-> foodType = R.drawable.vegetable
                    "Meat"-> foodType = R.drawable.proteins
                    "Dairy"-> foodType = R.drawable.dairy
                    "Other"-> foodType = R.drawable.fridge
                }

                if(!validateQuant(foodCount)){
                    val text = "Invalid Quantity"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                } else if(!validateExpDate(expireET)){
                    val text = "Invalid Expiration Date"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }  else if (newItem){
                    // add new item:
                    // add to viewModel for data persistence

                    viewModel.addNewFood(
                        foodName,
                        foodCount,
                        foodExpire,
                        radioButton.text.toString(),
                        foodNotes
                    )

                    // add to data list
                    foodList.add(Food(foodType,foodName,foodCount,foodExpire, false,
                        foodTypeString, foodNotes))

                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val intent = Intent(this, ListsActivity::class.java)

                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val notificationChannel = NotificationChannel("addChannel", "description", NotificationManager.IMPORTANCE_HIGH)
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        notificationManager.createNotificationChannel(notificationChannel)

                        val lastItem = foodList.last().name

                        val builder = Notification.Builder(this, "addChannel")
                            .setSmallIcon(R.drawable.fridge)
                            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.fridge))
                            .setContentIntent(pendingIntent)
                            .setContentTitle("New Item Added!")
                            .setContentText("$lastItem was just added to your FridgeFriend!")

                        notificationManager.notify(1234, builder.build())

                    } else {

                        val builder = Notification.Builder(this)
                            .setSmallIcon(R.drawable.fridge)
                            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.fridge))
                            .setContentIntent(pendingIntent)
                            .setContentTitle("New Item Added!")
                        notificationManager.notify(1234, builder.build())

                    }

                    val intent2 = Intent(this, ListsActivity::class.java)
                    startActivity(intent2)
                } else {
                    // update item's information
                    val updatedItem = Food(foodType,foodName,foodCount,foodExpire, false,
                        foodTypeString, foodNotes)
                    foodList[position] = updatedItem
                    val intent3 = Intent(this, ViewItemActivity::class.java)
                    intent3.putExtra("position", position)
                    startActivity(intent3)
                    finish()
                }
            } else {
                // Toast in case all information hasn't been filled out
                val text = "Please fill out all information"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }



        }
    }

    // Helper to change image depending of option chosen
    private fun updateImage(selectedOption: Int){
        val foodImage = findViewById<ImageView>(R.id.food_icon)
        val radioButton: RadioButton = findViewById(selectedOption)
        when(radioButton.text){
            "Fruit" -> foodImage.setImageResource(R.drawable.fruits)
            "Vegetable" -> foodImage.setImageResource(R.drawable.vegetable)
            "Meat" -> foodImage.setImageResource(R.drawable.proteins)
            "Dairy" -> foodImage.setImageResource(R.drawable.dairy)
            "Other" -> foodImage.setImageResource(R.drawable.fridge)
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