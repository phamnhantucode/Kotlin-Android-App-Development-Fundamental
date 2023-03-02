package com.learning.pokedexapplication.di

import com.learning.pokedexapplication.data.remote.responses.PokeApi
import com.learning.pokedexapplication.repository.PokemonRepository
import com.learning.pokedexapplication.util.Constants.BASE_URL_POKE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //singleton mean that this module alive along with our application
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi() = Retrofit.Builder()
        .baseUrl(BASE_URL_POKE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokeApi::class.java)

}
