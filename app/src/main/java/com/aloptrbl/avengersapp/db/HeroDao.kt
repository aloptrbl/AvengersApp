package com.aloptrbl.avengersapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aloptrbl.avengersapp.data.Hero

@Dao
interface HeroDao {
    @Query("SELECT * FROM hero")
    suspend fun getAll(): List<Hero>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hero: Hero)

    @Query("UPDATE hero SET rating = :rating WHERE title = :title")
    suspend fun updateRating(title: String, rating: Float)

    @Query("SELECT rating FROM hero WHERE title = :title")
    suspend fun getRatingByTitle(title: String): Float

}