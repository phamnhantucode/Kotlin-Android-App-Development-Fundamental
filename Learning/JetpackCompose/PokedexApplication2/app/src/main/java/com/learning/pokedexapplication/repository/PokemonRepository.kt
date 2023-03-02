package com.learning.pokedexapplication.repository

import android.accounts.NetworkErrorException
import com.learning.pokedexapplication.util.Resource
import com.learning.pokedexapplication.data.remote.responses.PokeApi
import com.learning.pokedexapplication.data.remote.responses.Pokemon
import com.learning.pokedexapplication.data.remote.responses.PokemonList
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import javax.inject.Inject

@ActivityScoped //live along with activity
class PokemonRepository @Inject constructor(
    private val api: PokeApi
){

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: java.lang.Exception) {
            when (e) {
                is HttpException -> {
                    return Resource.Error("Server error")
                }
                is NetworkErrorException -> {
                    return Resource.Error("Network error")
                }
                else -> {
                    return Resource.Error("Network error 2")
                }
            }
        }
        return Resource.Success(response)
    }

    suspend fun getPokemon(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e: java.lang.Exception) {
            when (e) {
                is HttpException -> {
                    return Resource.Error("Server error")
                }
                is NetworkErrorException -> {
                    return Resource.Error("Network error")
                }
                else -> {
                    return Resource.Error("Network error 2")
                }
            }
        }
        return Resource.Success(response)
    }
}