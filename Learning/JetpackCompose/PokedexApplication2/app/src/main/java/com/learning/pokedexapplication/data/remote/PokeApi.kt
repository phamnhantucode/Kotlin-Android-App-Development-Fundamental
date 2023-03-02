package com.learning.pokedexapplication.data.remote.responses

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,  //the number of pokemon be query in one time
        @Query("offset") offset: Int //offset example https://pokeapi.co/api/v2/pokemon?offset=20&limit=20
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String //path of route
    ): Pokemon
}