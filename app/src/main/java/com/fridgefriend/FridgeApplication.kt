package com.fridgefriend

import android.app.Application
import com.fridgefriend.data.FoodRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FridgeApplication : Application(){

    private val applicationScope = CoroutineScope(SupervisorJob())
    val database: FoodRoomDatabase by lazy { FoodRoomDatabase.getDatabase(this, applicationScope) }
}
