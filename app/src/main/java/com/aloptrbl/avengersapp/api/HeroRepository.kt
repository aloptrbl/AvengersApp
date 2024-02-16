package com.aloptrbl.avengersapp.api

import com.aloptrbl.avengersapp.data.Hero
import com.aloptrbl.avengersapp.db.HeroDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeroRepository @Inject constructor(private val heroApi: HeroAPI, private val heroDao: HeroDao) {
    suspend fun getCharacters() = heroApi.getCharacters()

    suspend fun getAllCharacters() = heroDao.getAll()
    suspend fun insertCharacter(hero: Hero) = heroDao.insert(hero)
    suspend fun updateRating(title: String, rating: Float) = heroDao.updateRating(title, rating)
    suspend fun getRatingByTitle(title: String) = heroDao.getRatingByTitle(title)
}
