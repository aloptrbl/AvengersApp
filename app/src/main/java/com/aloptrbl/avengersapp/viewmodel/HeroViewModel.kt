package com.aloptrbl.avengersapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.aloptrbl.avengersapp.api.HeroRepository
import com.aloptrbl.avengersapp.data.Hero
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
public class HeroViewModel @Inject constructor(private val heroRepository: HeroRepository) :
    ViewModel()  {

    suspend fun getCharacters() =
        heroRepository.getCharacters()

    fun getAllCharacters() = liveData {emit(heroRepository.getAllCharacters()) }

    suspend fun insertCharacters(hero: Hero) = heroRepository.insertCharacter(hero)

    fun getRatingByTitle(title: String) = liveData {
        emit(heroRepository.getRatingByTitle(title))
    }

    suspend fun updateRating(title: String, rating: Float) = heroRepository.updateRating(title, rating)

}