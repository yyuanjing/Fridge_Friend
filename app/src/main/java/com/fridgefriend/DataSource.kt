package com.fridgefriend

object DataSource {

    val foods: MutableList<Food> = mutableListOf(
        Food(
            R.drawable.dairy,
            "Milk",
            "1",
            "10/8/2022",
            "9/25/2022",
            false
        ),
        Food(
            R.drawable.fruits,
            "Apple",
            "3",
            "10/1/2022",
            "9/15/2022",
            true
        ),
        Food(
            R.drawable.proteins,
            "Ground Beef",
            "1",
            "9/28/2022",
            "7/25/2022",
            true
        ),
        Food(
            R.drawable.vegetable,
            "Potatoes",
            "5",
            "12/14/2022",
            "9/25/2022",
            false
        )
    )
}