package com.aloptrbl.avengersapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aloptrbl.avengersapp.data.Hero

@Database(entities = [Hero::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun heroDao(): HeroDao
}