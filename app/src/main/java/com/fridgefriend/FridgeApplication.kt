package com.fridgefriend

import android.app.Application
import com.fridgefriend.data.FoodRoomDatabase

class FridgeApplication : Application(){
    val database: FoodRoomDatabase by lazy { FoodRoomDatabase.getDatabase(this) }
}
