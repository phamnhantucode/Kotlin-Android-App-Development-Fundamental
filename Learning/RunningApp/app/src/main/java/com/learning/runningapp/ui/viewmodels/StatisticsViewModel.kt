package com.learning.runningapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.learning.runningapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {
    val totalTimeRun = mainRepository.getTotalTimeinMillis().asLiveData()
    val totalDistance = mainRepository.getTotalDistance().asLiveData()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurned().asLiveData()
    val totalAvgSpeed = mainRepository.getAvgSpeed().asLiveData()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate().asLiveData()
}