package com.fridgefriend

import androidx.lifecycle.*
import com.fridgefriend.data.FoodDao
import kotlinx.coroutines.launch
import com.fridgefriend.data.Food
import kotlinx.coroutines.Dispatchers

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all foods.
 *
 */
class FridgeViewModel(private val foodDao: FoodDao) : ViewModel() {
    val allFood: LiveData<List<Food>> = foodDao.getFoods().asLiveData()

    /**
     * Inserts the new Food into database.
     */
    fun addNewFood(foodName: String, foodQuantity: String, foodExpDate: String, foodType: String, foodNotes: String) {
        val newFood = getNewFoodEntry(foodName, foodQuantity, foodExpDate, foodType, foodNotes)
        insertFood(newFood)
    }

    /**
     * Launching a new coroutine to insert an food in a non-blocking way
     */
    private fun insertFood(food: Food) {
        viewModelScope.launch {
            foodDao.insert(food)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(foodName: String, foodQuantity: String, foodExpDate: String, foodType: String): Boolean {
        if (foodName.isBlank() || foodQuantity.isBlank() || foodExpDate.isBlank() || foodType.isBlank()) {
            return false
        }
        return true
    }

    fun deleteFood(pos: Int) {
        var food = foodDao.getFood(pos) as Food //KT
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to delete a food to the database here
            viewModelScope.launch {
                foodDao.delete(food)
            }
        }
    }

    /**
     * Returns an instance of the [Food] entity class with the food info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewFoodEntry(foodName: String, foodQuantity: String, foodExpDate: String, foodType: String, foodNotes: String): Food {
        return Food(
            name = foodName,
            quantity = foodQuantity,
            expDate = foodExpDate,
            type = foodType,
            notes = foodNotes,
            expired = false
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class FridgeViewModelFactory(private val foodDao: FoodDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FridgeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FridgeViewModel(foodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
