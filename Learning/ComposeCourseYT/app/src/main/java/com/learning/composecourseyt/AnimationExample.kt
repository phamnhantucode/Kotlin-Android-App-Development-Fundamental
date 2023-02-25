package com.learning.composecourseyt

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimateExample() {

    var sizeBox by remember {
        mutableStateOf(150.dp)
    }
    val size by animateDpAsState( // create animation with the change state if size box
        targetValue = sizeBox,
        tween(
            durationMillis = 2000,
            easing = LinearEasing
        )
    )

    val infiniteTransition = rememberInfiniteTransition() //create an infinite transition
    val color by infiniteTransition.animateColor(
        initialValue = Color.Yellow,
        targetValue = Color.Cyan,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 3000
            ),
            repeatMode = RepeatMode.Reverse //repeat animate like 1 -> 2 -> 1
        )
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            sizeBox += 50.dp
        }) {
            Text(text = "Click me")
        }
    }
}