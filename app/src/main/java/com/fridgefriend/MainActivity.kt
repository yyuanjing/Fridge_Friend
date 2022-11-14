package com.fridgefriend

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
//import com.fridgefriend.databinding.ActivityMainBinding
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {
    // private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // binding = ActivityMainBinding.inflate(layoutInflater)

        // Set listener to know when to move on to next activity (fridge layout)
        val button = findViewById<Button>(R.id.open_fridge_button)
        button.setOnClickListener(){
            scaleAndFade()
            val intent = Intent(this, ListsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun scaleAndFade(){
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)
        val fridgeImage = findViewById<ImageView>(R.id.fridge_image)

        // TODO: there's an issue with binding (couldn't get it to work so using findViewById
        //  instead for the time being, unsure about how to debug
        // val animatorFade = ObjectAnimator.ofFloat(binding.fridgeImage,View.ALPHA,0f)
        // val animatorScale = ObjectAnimator.ofPropertyValuesHolder(binding.fridgeImage,scaleX,scaleY)

        val animatorScale = ObjectAnimator.ofPropertyValuesHolder(fridgeImage,scaleX,scaleY)
        val animatorFade = ObjectAnimator.ofFloat(fridgeImage,View.ALPHA,0f)

        val button = findViewById<Button>(R.id.open_fridge_button)
        animatorScale.disableViewDuringAnimation(button)
        animatorScale.duration = 500
        animatorFade.duration = 500
        animatorScale.start()
        animatorFade.start()
    }

    fun ObjectAnimator.disableViewDuringAnimation(view: View){
        addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }
}