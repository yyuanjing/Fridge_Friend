package com.fridgefriend

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.addPauseListener
import androidx.viewpager2.widget.ViewPager2
import com.fridgefriend.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//        }

        supportActionBar?.hide()

        // Set listener to know when to move on to next activity (fridge layout)
        binding.openFridgeButton.setOnClickListener {
            val animatorFade = scaleAndFade()
            val thisForCoroutine = this
            GlobalScope.launch {
                delay(300)
                val intent = Intent(thisForCoroutine, ListsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun scaleAndFade(): ObjectAnimator{
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X,3f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 3f)

        val animatorFade = ObjectAnimator.ofFloat(binding.fridgeImage,View.ALPHA,0f)
        val animatorScale = ObjectAnimator.ofPropertyValuesHolder(binding.fridgeImage,scaleX,scaleY)

        animatorScale.disableViewDuringAnimation(binding.fridgeImage)
        animatorScale.duration = 600
        animatorFade.duration = 600
        animatorScale.start()
        animatorFade.start()
        return animatorFade
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View){
        addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
            }
        })
    }

    fun addNotification(){

        val channelId = "i.apps.notifications"
        val description = "Test notification"
        val intent = Intent(this, MainActivity::class.java)


        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.fridge)
                .setContentTitle("Welcome to Fridge Friend!")
                .setContentText("Add items and never forget what you have in your fridge again")
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }
}