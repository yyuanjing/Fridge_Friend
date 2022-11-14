package com.fridgefriend.data

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * from food ORDER BY name ASC")
    fun getFoods(): Flow<List<Food>>


    @Query("SELECT * from food WHERE id = :id")
    fun getFood(id: Int): Flow<Food>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Food into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food)


    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)
}

