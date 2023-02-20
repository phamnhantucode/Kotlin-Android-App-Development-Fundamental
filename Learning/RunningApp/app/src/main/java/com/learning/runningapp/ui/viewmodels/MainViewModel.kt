package com.learning.runningapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.learning.runningapp.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {
}