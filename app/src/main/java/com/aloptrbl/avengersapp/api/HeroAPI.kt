package com.aloptrbl.avengersapp.api

import com.aloptrbl.avengersapp.data.Hero
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroAPI {
    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): Hero
}