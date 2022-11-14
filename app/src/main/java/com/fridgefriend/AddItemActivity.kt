package com.fridgefriend


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.fridgefriend.FridgeViewModel
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import java.time.LocalDateTime

class AddItemActivity : AppCompatActivity() {

    private val foodList = DataSource.foods

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder

    private val viewModel: FridgeViewModel by activityViewModels {
        FridgeViewModelFactory(
            (activity?.application as FridgeApplication).database
                .foodDao()
        )
    }

    lateinit var food: Food

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

        //moved this to addNewItem for data persistence

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
                foodList.add(Food(foodType,foodName,foodCount,foodExpire, false))
                val intent2 = Intent(this, ListsActivity::class.java)
                startActivity(intent2)
            }

            if (validateQuant(foodCount) && validateExpi(foodExpire)) {
                viewModel.addNewFood(
                    foodName,
                    foodCount,
                    foodExpire,
                    radioButton.text.toString()
                )
                addNotification()
                val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
                findNavController().navigate(action)
            }

        }
    }

    fun addNotification(){

        val channelId = "i.apps.notifications"
        val description = "Test notification"
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val lastFood = foodList.last().name
        val exp = foodList.last().expiration_date
        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.fridge)
                .setContentTitle("FridgeFriend New Item Added: $lastFood")
                .setContentText("Your newly added $lastFood will expire on $exp")
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

// DATA PERSISTENCE???
//    fun addNewItem() {
//        val nameET = findViewById<View>(R.id.input_name) as EditText
//        val foodName = nameET.text.toString()
//
//        val quantityET = findViewById<View>(R.id.input_quantity) as EditText
//        val foodCount = quantityET.text.toString()
//
//        val expireET = findViewById<View>(R.id.input_exp_date) as EditText
//        val foodExpire = expireET.text.toString()
//
//        val radioGroup: RadioGroup = findViewById(R.id.type_options)
//        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
//        val radioButton: RadioButton = findViewById(selectedOption)
//
//        var foodType = R.drawable.fruits
//        when (radioButton.text){
//            "Fruit"-> foodType = R.drawable.fruits
//            "Vegetable"-> foodType = R.drawable.vegetable
//            "Meat"-> foodType = R.drawable.proteins
//            "Dairy"-> foodType = R.drawable.dairy
//            "Other"-> foodType = R.drawable.fridge
//        }
//
//        if(!validateQuant(foodCount)){
//            val text = "Invalid Quantity"
//            val duration = Toast.LENGTH_SHORT
//            val toast = Toast.makeText(applicationContext, text, duration)
//            toast.show()
//        }
//        else if(!validateExpi(foodExpire)){
//            val text = "Invalid Expiration Date"
//            val duration = Toast.LENGTH_SHORT
//            val toast = Toast.makeText(applicationContext, text, duration)
//            toast.show()
//        }
//        else{
//            foodList.add(Food(foodType,foodName,foodCount,foodExpire, "DATE ADDED", false))
//            val intent2 = Intent(this, ListsActivity::class.java)
//            startActivity(intent2)
//        }
//
//        if (validateQuant(foodCount) && validateExpi(foodExpire)) {
//            viewModel.addNewFood(
//                foodName,
//                foodCount,
//                foodExpire,
//                radioButton.text.toString()
//            )
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val doneButton = findViewById<Button>(R.id.button_done)
//        doneButton.setOnClickListener(){
//            addNewItem()
//        }
//    }

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