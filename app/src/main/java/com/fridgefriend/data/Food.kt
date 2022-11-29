package com.fridgefriend.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val quantity: String,
    val expDate: String,
    val type: String,
    val notes: String,
    val expired: Boolean
)
