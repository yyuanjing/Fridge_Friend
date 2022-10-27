package com.fridgefriend

import androidx.annotation.DrawableRes

data class Food (
    @DrawableRes val imageResourceId: Int, //r.drawable.dairy for example
    val name: String,
    val quantity: String,
    val expiration_date: String
)