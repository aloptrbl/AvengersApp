package com.aloptrbl.avengersapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hero(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val rating: Float,
    val image: Int,
)
