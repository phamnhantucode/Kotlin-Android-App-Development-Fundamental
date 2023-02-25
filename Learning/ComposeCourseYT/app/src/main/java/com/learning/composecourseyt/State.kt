package com.learning.composecourseyt

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf

@Composable
fun DerivedStateDemo() {
    var counter by remember {
        mutableStateOf(0)
    }

    val counterText by derivedStateOf {
        "The counter text is $counter"
    }

    Button(onClick = { counter++ }) {
        Text(text = counterText)
    }
}