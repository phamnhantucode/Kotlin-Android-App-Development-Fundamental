package com.learning.pokedexapplication.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.learning.pokedexapplication.data.model.PokedexListEntry
import com.learning.pokedexapplication.repository.PokemonRepository
import com.learning.pokedexapplication.util.Constants.BASE_IMGURL_POKE
import com.learning.pokedexapplication.util.Constants.PAGE_SIZE
import com.learning.pokedexapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)


    var cachePokemonList = listOf<PokedexListEntry>()
    var isSearching = mutableStateOf(false)
    var isStartSearching = true
    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {

        viewModelScope.launch(Dispatchers.Default) {
            var listToSearch = if (isStartSearching) {
                pokemonList.value
            } else {
                cachePokemonList
            }
            if (query.isEmpty()) {
                isSearching.value = false
                isStartSearching = true
                pokemonList.value = cachePokemonList
                return@launch
            }
            val result = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) || it.number.equals(query.trim())
            }
            Timber.d(query)

            if (isStartSearching) {
                cachePokemonList = pokemonList.value
                isStartSearching = false
            }
            pokemonList.value = result
            isSearching.value = true
        }
    }



    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, curPage * 20)
            when (result) {
                is Resource.Success -> {
                    endReached.value =
                        curPage * PAGE_SIZE >= result.data!!.count //endReached = true until all pokemons were fetched
                    val pokemonEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = String.format(BASE_IMGURL_POKE, number.toInt())
                        PokedexListEntry(entry.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }, imageUrl = url, number = number.toInt())
                    }
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokemonEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinished: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888,
            true
        ) //copy an bitmap which each pixel is stored on 4 bytes
        Palette.from(bitmap).generate {
            it?.dominantSwatch?.rgb?.let { //The dominant swatch property returns the most dominant color swatch from the
                onFinished(Color(it))
            }
        }
    }
}
