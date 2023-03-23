package com.phamnhantucode.trackmysleepquality

import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.phamnhantucode.trackmysleepquality.SleepDatabaseDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val sleepData = MutableStateFlow<List<SleepNight>>(listOf())
    val isStarting = MutableStateFlow(false)
    val currentSleep = MutableStateFlow<SleepNight?>(null).also {
        viewModelScope.launch {
            it.onEach {
                it?.let {
                    isStarting.emit(it.startTimeMilli == it.endTimeMilli)
                }
            }.collect()
        }
    }

    init {
        viewModelScope.launch {
            database.getAllNights().collect {
                sleepData.emit(it)
            }
            currentSleep.update {
                val current = database.getTonight()
                current?.let {
                    isStarting.emit(current.endTimeMilli == current.startTimeMilli)
                }
                current
            }
        }
    }

    fun onStart() {
        viewModelScope.launch {
//            isStarting.emit(true)
            database.insert(SleepNight())
            currentSleep.emit(database.getTonight())
        }
    }

    fun onStop() {
        viewModelScope.launch {
            isStarting.emit(false)
            currentSleep.emit(
                currentSleep.value?.copy(endTimeMilli = System.currentTimeMillis())
                    ?: return@launch
            )
            currentSleep.value?.let { updateNight(it) }
        }
    }

    fun onClear() {
        viewModelScope.launch {
            database.clear()
            currentSleep.emit(null)
        }
    }

//    fun on

    private suspend fun updateNight(night: SleepNight) {
        database.update(night)
    }

    private suspend fun insert(night: SleepNight) {
        database.insert(night)
    }


}

class SleepTrackerViewModelFactory(
    private val dataSource: SleepDatabaseDao,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
            return SleepTrackerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}