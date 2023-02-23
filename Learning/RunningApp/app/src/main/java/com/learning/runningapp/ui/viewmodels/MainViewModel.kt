package com.learning.runningapp.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learning.runningapp.db.Run
import com.learning.runningapp.other.SortType
import com.learning.runningapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val handle: SavedStateHandle,
    val mainRepository: MainRepository
) : ViewModel() {
    private fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }


    private val runsSortedByDate = mainRepository.getAllRunsSortedByDate().asLiveData()
    private val runsSortedByDistance = mainRepository.getAllRunsSortedByDistance().asLiveData()
    private val runsSortedByCaloriesdBurned =  mainRepository.getAllRunsSortedByCaloriesBurned().asLiveData()
    private val runsSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis().asLiveData()
    private val runsSortedByAvgSpeed = mainRepository.getAllRunsSortedByAvgSpeed().asLiveData()
    var runsSorted = MediatorLiveData<List<Run>>()
    var sortType = SortType.DATE
    init {
        runsSorted.addSource(runsSortedByDate) { result ->
            if(sortType == SortType.DATE) {
                result?.let { runsSorted.value = it }
            }
        }
        runsSorted.addSource(runsSortedByAvgSpeed) { result ->
            if(sortType == SortType.AVG_SPEED) {
                result?.let { runsSorted.value = it }
            }
        }
        runsSorted.addSource(runsSortedByCaloriesdBurned) { result ->
            if(sortType == SortType.CALORIES_BURNED) {
                result?.let { runsSorted.value = it }
            }
        }
        runsSorted.addSource(runsSortedByDistance) { result ->
            if(sortType == SortType.DISTANCE) {
                result?.let { runsSorted.value = it }
            }
        }
        runsSorted.addSource(runsSortedByTimeInMillis) { result ->
            if(sortType == SortType.RUNNING_TIME) {
                result?.let { runsSorted.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType) {
        SortType.DATE -> runsSortedByDate.value?.let { runsSorted.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runsSorted.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runsSorted.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value?.let { runsSorted.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCaloriesdBurned.value?.let { runsSorted.value = it }
    }.also {
        this.sortType = sortType
    }


    private fun getRun(
        img: Bitmap,
        timestamp: Long,
        avgSpeedInKMH: Float,
        distanceInMeters: Int,
        timeInMillis: Long,
        caloriesBurned: Int
    ): Run {
        return Run(img, timestamp, avgSpeedInKMH, distanceInMeters, timeInMillis,caloriesBurned)
    }

    fun insertRun(
        img: Bitmap,
        timestamp: Long,
        avgSpeedInKMH: Float,
        distanceInMeters: Int,
        timeInMillis: Long,
        caloriesBurned: Int
    ) {
        insertRun(getRun(img, timestamp, avgSpeedInKMH, distanceInMeters, timeInMillis,caloriesBurned))
    }


}