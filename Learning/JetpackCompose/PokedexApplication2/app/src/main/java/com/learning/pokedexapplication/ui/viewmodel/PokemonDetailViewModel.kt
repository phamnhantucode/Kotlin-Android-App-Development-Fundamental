package com.learning.pokedexapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.pokedexapplication.data.model.PokedexListEntry
import com.learning.pokedexapplication.data.remote.responses.Pokemon
import com.learning.pokedexapplication.repository.PokemonRepository
import com.learning.pokedexapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        return repository.getPokemon(name)
    }
}