package com.fridgefriend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fridgefriend.data.FoodDao
import kotlinx.coroutines.launch
import com.fridgefriend.data.Food

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all foods.
 *
 */
class FridgeViewModel(private val foodDao: FoodDao) : ViewModel() {

    /**
     * Inserts the new Food into database.
     */
    fun addNewFood(foodName: String, foodQuantity: String, foodExpDate: String, foodType: String) {
        val newFood = getNewFoodEntry(foodName, foodQuantity, foodExpDate, foodType)
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

    /**
     * Returns an instance of the [Food] entity class with the food info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewFoodEntry(foodName: String, foodQuantity: String, foodExpDate: String, foodType: String): Food {
        return Food(
            name = foodName,
            quantity = foodQuantity,
            expDate = foodExpDate,
            type = foodType
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
