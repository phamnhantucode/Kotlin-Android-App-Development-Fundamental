package com.learning.pokedexapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp //just mark this is our application class for dagger hilt
class PokedexApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) //just plant a timber here
    }
}